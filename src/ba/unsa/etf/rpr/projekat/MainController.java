package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class MainController {

    public TableView<Appointment> tableViewAppointments;
    public TableView<Patient> tableViewPatients;
    public TableView<Doctor> tableViewDoctors;
    public TableView<Treatment> tableViewTreatments;
    public TableView<MedicalMajor> tableViewMedicalMajors;
    public TableView<Disease> tableViewDiseases;

    public TableColumn columnAppointmentsId;
    public TableColumn columnAppointmentsPatient;
    public TableColumn columnAppointmentsDoctor;
    public TableColumn columnAppointmentsDiseases;
    public TableColumn columnAppointmentsDate;
    public TableColumn columnAppointmentsTime;
    public TableColumn columnPatientsId;
    public TableColumn columnPatientsFirstName;
    public TableColumn columnPatientsLastName;
    public TableColumn columnPatientsBirthDate;
    public TableColumn columnPatientsCitizenNumber;
    public TableColumn columnPatientsPhoneNumber;
    public TableColumn columnDoctorsId;
    public TableColumn columnDoctorsFirstName;
    public TableColumn columnDoctorsLastName;
    public TableColumn columnDoctorsBirthDate;
    public TableColumn columnDoctorsMedicalMajor;
    public TableColumn columnDoctorsPhoneNumber;
    public TableColumn columnTreatmentsId;
    public TableColumn columnTreatmentsName;
    public TableColumn columnMedicalMajorsId;
    public TableColumn columnMedicalMajorsName;
    public TableColumn columnDiseasesId;
    public TableColumn columnDiseasesName;
    public TableColumn columnDiseasesMedicalMajor;

    public TextField fieldSearchAppointment;
    public TextField fieldSearchPatient;
    public TextField fieldSearchDoctor;
    public TextField fieldSearchTreatment;
    public TextField fieldSearchMedicalMajor;
    public TextField fieldSearchDisease;

    private HospitalDAO dao;

    private ObservableList<Appointment> listAppointments;
    private ObservableList<Patient> listPatients;
    private ObservableList<Doctor> listDoctors;
    private ObservableList<Treatment> listTreatments;
    private ObservableList<MedicalMajor> listMedicalMajors;
    private ObservableList<Disease> listDiseases;

    public MainController() {
        dao = HospitalDAO.getInstance();
        listAppointments = FXCollections.observableArrayList(dao.appointments());
        listPatients = FXCollections.observableArrayList(dao.patients());
        listDoctors = FXCollections.observableArrayList(dao.doctors());
        listTreatments = FXCollections.observableArrayList(dao.treatments());
        listMedicalMajors = FXCollections.observableArrayList(dao.medicalMajors());
        listDiseases = FXCollections.observableArrayList(dao.diseases());
    }

    @FXML
    public void initialize() {
        tableViewAppointments.setItems(listAppointments);
        columnAppointmentsId.setCellValueFactory(new PropertyValueFactory("id"));
        columnAppointmentsPatient.setCellValueFactory(new PropertyValueFactory("patient"));
        columnAppointmentsDoctor.setCellValueFactory(new PropertyValueFactory("doctor"));
        columnAppointmentsDiseases.setCellValueFactory(new PropertyValueFactory("disease"));
        columnAppointmentsDate.setCellValueFactory(new PropertyValueFactory("appointmentDate"));
        columnAppointmentsTime.setCellValueFactory(new PropertyValueFactory("appointmentTime"));

        tableViewPatients.setItems(listPatients);
        columnPatientsId.setCellValueFactory(new PropertyValueFactory("id"));
        columnPatientsFirstName.setCellValueFactory(new PropertyValueFactory("firstName"));
        columnPatientsLastName.setCellValueFactory(new PropertyValueFactory("lastName"));
        columnPatientsBirthDate.setCellValueFactory(new PropertyValueFactory("birthDate"));
        columnPatientsCitizenNumber.setCellValueFactory(new PropertyValueFactory("citizenNumber"));
        columnPatientsPhoneNumber.setCellValueFactory(new PropertyValueFactory("phoneNumber"));

        tableViewDoctors.setItems(listDoctors);
        columnDoctorsId.setCellValueFactory(new PropertyValueFactory("id"));
        columnDoctorsFirstName.setCellValueFactory(new PropertyValueFactory("firstName"));
        columnDoctorsLastName.setCellValueFactory(new PropertyValueFactory("lastName"));
        columnDoctorsBirthDate.setCellValueFactory(new PropertyValueFactory("birthDate"));
        columnDoctorsMedicalMajor.setCellValueFactory(new PropertyValueFactory("medicalMajor"));
        columnDoctorsPhoneNumber.setCellValueFactory(new PropertyValueFactory("phoneNumber"));

        tableViewTreatments.setItems(listTreatments);
        columnTreatmentsId.setCellValueFactory(new PropertyValueFactory("id"));
        columnTreatmentsName.setCellValueFactory(new PropertyValueFactory("treatmentName"));

        tableViewMedicalMajors.setItems(listMedicalMajors);
        columnMedicalMajorsId.setCellValueFactory(new PropertyValueFactory("id"));
        columnMedicalMajorsName.setCellValueFactory(new PropertyValueFactory("medicalMajorName"));

        tableViewDiseases.setItems(listDiseases);
        columnDiseasesId.setCellValueFactory(new PropertyValueFactory("id"));
        columnDiseasesName.setCellValueFactory(new PropertyValueFactory("diseaseName"));
        ;
        columnDiseasesMedicalMajor.setCellValueFactory(new PropertyValueFactory("medicalMajor"));

        fieldSearchAppointment.textProperty().addListener((obs, oldSearch, newSearch) -> {
            if (newSearch.isEmpty()) {
                tableViewAppointments.setItems(listAppointments);
            } else {
                ObservableList<Appointment> searched = FXCollections.observableArrayList();
                for (Appointment i : listAppointments) {
                    if (i.getDoctor().getFirstName().toLowerCase().contains(fieldSearchAppointment.getText().toLowerCase()) ||
                            i.getDoctor().getLastName().toLowerCase().contains(fieldSearchAppointment.getText().toLowerCase()) ||
                            i.getPatient().getFirstName().toLowerCase().contains(fieldSearchAppointment.getText().toLowerCase()) ||
                            i.getPatient().getLastName().toLowerCase().contains(fieldSearchAppointment.getText().toLowerCase()) ||
                            i.getDisease().getDiseaseName().toLowerCase().contains(fieldSearchAppointment.getText().toLowerCase()))
                        searched.add(i);
                }
                tableViewAppointments.setItems(searched);
            }
        });
        fieldSearchPatient.textProperty().addListener((obs, oldSearch, newSearch) -> {
            if (newSearch.isEmpty()) {
                tableViewPatients.setItems(listPatients);
            } else {
                ObservableList<Patient> searched = FXCollections.observableArrayList();
                for (Patient i : listPatients) {
                    if (i.getFirstName().toLowerCase().contains(fieldSearchPatient.getText().toLowerCase()) ||
                            i.getLastName().toLowerCase().contains(fieldSearchPatient.getText().toLowerCase())
                    )
                        searched.add(i);
                }
                tableViewPatients.setItems(searched);
            }
        });
        fieldSearchDoctor.textProperty().addListener((obs, oldSearch, newSearch) -> {
            if (newSearch.isEmpty()) {
                tableViewDoctors.setItems(listDoctors);
            } else {
                ObservableList<Doctor> searched = FXCollections.observableArrayList();
                for (Doctor i : listDoctors) {
                    if (i.getFirstName().toLowerCase().contains(fieldSearchDoctor.getText().toLowerCase()) ||
                            i.getLastName().toLowerCase().contains(fieldSearchDoctor.getText().toLowerCase())
                    )
                        searched.add(i);
                }
                tableViewDoctors.setItems(searched);
            }
        });
        fieldSearchTreatment.textProperty().addListener((obs, oldSearch, newSearch) -> {
            if (newSearch.isEmpty()) {
                tableViewTreatments.setItems(listTreatments);
            } else {
                ObservableList<Treatment> searched = FXCollections.observableArrayList();
                for (Treatment i : listTreatments) {
                    if (i.getTreatmentName().toLowerCase().contains(fieldSearchTreatment.getText().toLowerCase()))
                        searched.add(i);
                }
                tableViewTreatments.setItems(searched);
            }
        });
        fieldSearchMedicalMajor.textProperty().addListener((obs, oldSearch, newSearch) -> {
            if (newSearch.isEmpty()) {
                tableViewMedicalMajors.setItems(listMedicalMajors);
            } else {
                ObservableList<MedicalMajor> searched = FXCollections.observableArrayList();
                for (MedicalMajor i : listMedicalMajors) {
                    if (i.getMedicalMajorName().toLowerCase().contains(fieldSearchMedicalMajor.getText().toLowerCase()))
                        searched.add(i);
                }
                tableViewMedicalMajors.setItems(searched);
            }
        });
        fieldSearchDisease.textProperty().addListener((obs, oldSearch, newSearch) -> {
            if (newSearch.isEmpty()) {
                tableViewDiseases.setItems(listDiseases);
            } else {
                ObservableList<Disease> searched = FXCollections.observableArrayList();
                for (Disease i : listDiseases) {
                    if (i.getDiseaseName().toLowerCase().contains(fieldSearchDisease.getText().toLowerCase()))
                        searched.add(i);
                }
                tableViewDiseases.setItems(searched);
            }
        });

        tableViewAppointments.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                editAppointmentAction();
            }
        });
        tableViewPatients.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                editPatientAction();
            }
        });
        tableViewDoctors.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                editDoctorAction();
            }
        });
        tableViewTreatments.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                editTreatmentAction();
            }
        });
        tableViewMedicalMajors.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                editMedicalMajorAction();
            }
        });
        tableViewDiseases.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                editDiseaseAction();
            }
        });

    }

    public void removeAppointmentAction() {
        if (tableViewAppointments.getSelectionModel().getSelectedItem() == null) return;
        Appointment appointment = tableViewAppointments.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Removing appointment confirmation");
        alert.setHeaderText("Are you sure you want to remove this appointment?");
        alert.setContentText("It will be deleted from patients' and doctors' records");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            dao.removeAppointment(appointment);
            listAppointments.setAll(dao.appointments());
            fieldSearchAppointment.setText("");
        }
    }

    public void removePatientAction() {
        Patient patient = tableViewPatients.getSelectionModel().getSelectedItem();
        if (patient == null) return;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Removing patient confirmation");
        alert.setHeaderText("Are you sure you want to remove patient " + patient + "?");
        alert.setContentText("He/She will be deleted from doctors' records");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            dao.removePatient(patient);
            listPatients.setAll(dao.patients());
            listAppointments.setAll(dao.appointments());
            fieldSearchPatient.setText("");
            fieldSearchAppointment.setText("");
        }
    }

    public void removeDoctorAction() {
        Doctor doctor = tableViewDoctors.getSelectionModel().getSelectedItem();
        if (doctor == null) return;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Removing doctor confirmation");
        alert.setHeaderText("Are you sure you want to remove doctor " + doctor + "?");
        alert.setContentText("He/She will be deleted from patients' records");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            dao.removeDoctor(doctor);
            listDoctors.setAll(dao.doctors());
            listAppointments.setAll(dao.appointments());
            fieldSearchDoctor.setText("");
            fieldSearchAppointment.setText("");
        }
    }

    public void removeTreatmentAction() {
        Treatment treatment = tableViewTreatments.getSelectionModel().getSelectedItem();
        if (treatment == null) return;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Removing treatment confirmation");
        alert.setHeaderText("Are you sure you want to remove treatment " + treatment + "?");
        alert.setContentText("It will be deleted from appointments and diseases where it is used");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            dao.removeTreatment(treatment);
            listTreatments.setAll(dao.treatments());
            fieldSearchTreatment.setText("");
        }
    }

    public void removeMedicalMajorAction() {
        MedicalMajor medicalMajor = tableViewMedicalMajors.getSelectionModel().getSelectedItem();
        if (medicalMajor == null) return;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Removing medical major confirmation");
        alert.setHeaderText("Are you sure you want to remove medical major " + medicalMajor + "?");
        alert.setContentText("Doctors and diseases with this medical major will be deleted");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            dao.removeMedicalMajor(medicalMajor);
            listMedicalMajors.setAll(dao.medicalMajors());
            listDoctors.setAll(dao.doctors());
            listDiseases.setAll(dao.diseases());
            listAppointments.setAll(dao.appointments());
            fieldSearchDisease.setText("");
            fieldSearchAppointment.setText("");
            fieldSearchMedicalMajor.setText("");
            fieldSearchDoctor.setText("");
        }
    }

    public void removeDiseaseAction() {
        Disease disease = tableViewDiseases.getSelectionModel().getSelectedItem();
        if (disease == null) return;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Removing disease confirmation");
        alert.setHeaderText("Are you sure you want to remove disease " + disease + "?");
        alert.setContentText("It will be deleted from patients' and doctors' records");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            dao.removeDisease(disease);
            listDiseases.setAll(dao.diseases());
            listAppointments.setAll(dao.appointments());
            fieldSearchDisease.setText("");
            fieldSearchAppointment.setText("");
        }
    }

    private void addOrEditAppointmentAction(Appointment appointment) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/appointment.fxml"));
            AppointmentController appointmentController = new AppointmentController(dao, appointment);
            loader.setController(appointmentController);
            loader.load();

            stage.setTitle("Appointment");
            stage.setScene(new Scene(loader.getRoot(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setMaxWidth(400);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

            stage.setOnHiding(event -> {
                int selectedIndex = tableViewAppointments.getSelectionModel().getSelectedIndex();
                listAppointments.setAll(dao.appointments());
                tableViewAppointments.getSelectionModel().select(selectedIndex);
                if(appointment == null) tableViewAppointments.getSelectionModel().selectLast();

            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addAppointmentAction() {
        addOrEditAppointmentAction(null);
    }
    public void editAppointmentAction() {
        Appointment appointment = tableViewAppointments.getSelectionModel().getSelectedItem();
        if(appointment == null) return;
        addOrEditAppointmentAction(appointment);
    }

    private void addOrEditPatientAction(Patient patient) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/patient.fxml"));
            PatientController patientController = new PatientController(dao, patient);
            loader.setController(patientController);
            loader.load();

            stage.setTitle("Patient");
            stage.setScene(new Scene(loader.getRoot(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setMaxWidth(400);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

            stage.setOnHiding(event -> {
                int selectedIndex = tableViewPatients.getSelectionModel().getSelectedIndex();
                listPatients.setAll(dao.patients());
                listAppointments.setAll(dao.appointments());
                tableViewPatients.getSelectionModel().select(selectedIndex);
                if(patient == null) tableViewPatients.getSelectionModel().selectLast();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addPatientAction() {
        addOrEditPatientAction(null);
    }
    public void editPatientAction() {
        Patient patient = tableViewPatients.getSelectionModel().getSelectedItem();
        if(patient == null) return;
        addOrEditPatientAction(patient);
    }

    private void addOrEditDoctorAction(Doctor doctor) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/doctor.fxml"));
            DoctorController doctorController = new DoctorController(dao, doctor);
            loader.setController(doctorController);
            loader.load();

            stage.setTitle("Doctor");
            stage.setScene(new Scene(loader.getRoot(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setMaxWidth(400);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

            stage.setOnHiding(event -> {
                int selectedIndex = tableViewDoctors.getSelectionModel().getSelectedIndex();
                listDoctors.setAll(dao.doctors());
                listAppointments.setAll(dao.appointments());
                tableViewDoctors.getSelectionModel().select(selectedIndex);
                if(doctor == null) tableViewDoctors.getSelectionModel().selectLast();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addDoctorAction() {
        addOrEditDoctorAction(null);
    }
    public void editDoctorAction() {
        Doctor doctor = tableViewDoctors.getSelectionModel().getSelectedItem();
        if(doctor == null) return;
        addOrEditDoctorAction(doctor);
    }

    private void addOrEditTreatmentAction(Treatment treatment) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/treatment.fxml"));
            TreatmentController treatmentController = new TreatmentController(dao, treatment);
            loader.setController(treatmentController);
            loader.load();

            stage.setTitle("Treatment");
            stage.setScene(new Scene(loader.getRoot(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setMaxWidth(400);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

            stage.setOnHiding(event -> {
                int selectedIndex = tableViewTreatments.getSelectionModel().getSelectedIndex();
                listTreatments.setAll(dao.treatments());
                tableViewTreatments.getSelectionModel().select(selectedIndex);
                if(treatment == null) tableViewTreatments.getSelectionModel().selectLast();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addTreatmentAction() {
        addOrEditTreatmentAction(null);
    }
    public void editTreatmentAction() {
        Treatment treatment = tableViewTreatments.getSelectionModel().getSelectedItem();
        if (treatment == null) return;
        addOrEditTreatmentAction(treatment);
    }

    private void addOrEditMedicalMajorAction(MedicalMajor medicalMajor) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/medical_major.fxml"));
            MedicalMajorController medicalMajorController = new MedicalMajorController(dao, medicalMajor);
            loader.setController(medicalMajorController);
            loader.load();

            stage.setTitle("Medical major");
            stage.setScene(new Scene(loader.getRoot(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setMaxWidth(400);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

            stage.setOnHiding(event -> {
                int selectedIndex = tableViewMedicalMajors.getSelectionModel().getSelectedIndex();
                listMedicalMajors.setAll(dao.medicalMajors());
                tableViewMedicalMajors.getSelectionModel().select(selectedIndex);
                if(medicalMajor == null) tableViewMedicalMajors.getSelectionModel().selectLast();
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addMedicalMajorAction() {
        addOrEditMedicalMajorAction(null);
    }
    public void editMedicalMajorAction() {
        MedicalMajor medicalMajor = tableViewMedicalMajors.getSelectionModel().getSelectedItem();
        if (medicalMajor == null) return;
        addOrEditMedicalMajorAction(medicalMajor);
    }

    private void addOrEditDiseaseAction(Disease disease) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/disease.fxml"));
            DiseaseController diseaseController = new DiseaseController(dao, disease);
            loader.setController(diseaseController);
            loader.load();

            stage.setTitle("Disease");
            stage.setScene(new Scene(loader.getRoot(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setMaxWidth(400);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

            stage.setOnHiding(event -> {
                int selectedIndex = tableViewDiseases.getSelectionModel().getSelectedIndex();
                listDiseases.setAll(dao.diseases());
                listAppointments.setAll(dao.appointments());
                tableViewDiseases.getSelectionModel().select(selectedIndex);
                if(disease == null) tableViewDiseases.getSelectionModel().selectLast();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addDiseaseAction() {
        addOrEditDiseaseAction(null);
    }
    public void editDiseaseAction() {
        Disease disease = tableViewDiseases.getSelectionModel().getSelectedItem();
        if (disease == null) return;
        addOrEditDiseaseAction(disease);
    }

    public void aboutAction() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Hospital");
        alert.setHeaderText(null);
        alert.setContentText("Hospital 1.0.0\n\n" +
                "Github repository: https://github.com/yaly1/rpr-projekat\n\n" +
                "TM and © 2020 Hospital dev\n" +
                "All Rights Reserved.");
        alert.showAndWait();
    }
    public void exitAction() {
        System.exit(0);
    }
}
