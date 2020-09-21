package ba.unsa.etf.rpr.projekat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

import static ba.unsa.etf.rpr.projekat.Person.*;
import static ba.unsa.etf.rpr.projekat.Patient.*;
import static ba.unsa.etf.rpr.projekat.Doctor.*;

public class HospitalDAO {
    private static HospitalDAO instance = null;
    private Connection connection;

    private PreparedStatement getAppointmentsStatement, getPatientsStatement, getDoctorsStatement, getTreatmentsStatement,
            getMedicalMajorsStatement, getDiseasesStatement, getMedicalMajorStatement, getPatientStatement, getDoctorStatement, getDiseaseStatement;

    private HospitalDAO() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:hospital.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            getPatientsStatement = connection.prepareStatement("SELECT * FROM patient");
        } catch (SQLException e1) {
            regenerateBase();
            try {
                getPatientsStatement = connection.prepareStatement("SELECT * FROM patient");
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        try {
            getAppointmentsStatement = connection.prepareStatement("SELECT * FROM appointment");
            getDoctorsStatement = connection.prepareStatement("SELECT * FROM doctor");
            getTreatmentsStatement = connection.prepareStatement("SELECT * FROM treatment");
            getMedicalMajorsStatement = connection.prepareStatement("SELECT * FROM medical_major");
            getDiseasesStatement = connection.prepareStatement("SELECT * FROM disease");

            getPatientStatement = connection.prepareStatement("SELECT * FROM patient WHERE id = ?");
            getDoctorStatement = connection.prepareStatement("SELECT * FROM doctor WHERE id = ?");
            getDiseaseStatement = connection.prepareStatement("SELECT * FROM disease WHERE id = ?");
            getMedicalMajorStatement = connection.prepareStatement("SELECT major_name FROM medical_major WHERE id = ?");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void regenerateBase() {
        Scanner ulaz;
        try {
            ulaz = new Scanner(new FileInputStream("hospital.db.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if (sqlUpit.length() > 1 && sqlUpit.charAt(sqlUpit.length() - 1) == ';') {
                    try {
                        Statement stmt = connection.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            ulaz.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static HospitalDAO getInstance() {
        if (instance == null) instance = new HospitalDAO();
        return instance;
    }

    public static void removeInstance() {
        if (instance != null) {
            try {
                instance.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        instance = null;
    }

    public ArrayList<Appointment> appointments() {
        ArrayList<Appointment> appointments = new ArrayList<>();
        try {
            ResultSet rs = getAppointmentsStatement.executeQuery();
            while (rs.next()) {
                appointments.add(getAppointmentFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    private Appointment getAppointmentFromResultSet(ResultSet rs) throws SQLException {

        ResultSet rs2;
        getPatientStatement.setInt(1, rs.getInt(2));
        rs2 = getPatientStatement.executeQuery();
        Patient patient = getPatientFromResultSet(rs2);

        getDoctorStatement.setInt(1, rs.getInt(3));
        rs2 = getDoctorStatement.executeQuery();
        Doctor doctor = getDoctorFromResultSet(rs2);

        getDiseaseStatement.setInt(1, rs.getInt(4));
        rs2 = getDiseaseStatement.executeQuery();
        Disease disease = getDiseaseFromResultSet(rs2);

        LocalDate date = LocalDate.of(
                Integer.valueOf(rs.getString(5).substring(0, 4)),
                Integer.valueOf(rs.getString(5).substring(5, 7)),
                Integer.valueOf(rs.getString(5).substring(8, 10)));

        LocalTime time = LocalTime.of(
                Integer.valueOf(rs.getString(6).substring(0, 2)),
                Integer.valueOf(rs.getString(6).substring(3, 5)));

        return new Appointment(rs.getInt(1), patient, doctor, disease,date,time);
    }

    public ArrayList<Patient> patients() {
        ArrayList<Patient> patients = new ArrayList<>();
        try {
            ResultSet rs = getPatientsStatement.executeQuery();
            while (rs.next()) {
                patients.add(getPatientFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    private Patient getPatientFromResultSet(ResultSet rs) throws SQLException {
        LocalDate date = LocalDate.of(
                Integer.valueOf(rs.getString(5).substring(0, 4)),
                Integer.valueOf(rs.getString(5).substring(5, 7)),
                Integer.valueOf(rs.getString(5).substring(8, 10)));
        CitizenNumber citizenNumber = new CitizenNumber(rs.getString(6));
        PhoneNumber phoneNumber = new PhoneNumber(rs.getString(7));
        EmailAddress emailAddress = new EmailAddress(rs.getString(8));
        Gender gender = !(rs.getString(9).equals("Male")) ? !(rs.getString(9).equals("Female")) ? null : Gender.FEMALE : Gender.MALE;
        BloodType bloodType = BloodType.fromString(rs.getString(10));
        Height height = new Height(rs.getInt(11));
        Weight weight = new Weight(rs.getDouble(12));

        return new Patient(rs.getInt(1), rs.getString(2), rs.getString(3),
                rs.getString(4), date, citizenNumber, phoneNumber, emailAddress, gender, bloodType, height, weight);
    }

    public ArrayList<Doctor> doctors() {
        ArrayList<Doctor> doctors = new ArrayList<>();
        try {
            ResultSet rs = getDoctorsStatement.executeQuery();
            while (rs.next()) {
                doctors.add(getDoctorFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctors;
    }

    private Doctor getDoctorFromResultSet(ResultSet rs) throws SQLException {
        LocalDate date = LocalDate.of(
                Integer.valueOf(rs.getString(5).substring(0, 4)),
                Integer.valueOf(rs.getString(5).substring(5, 7)),
                Integer.valueOf(rs.getString(5).substring(8, 10)));
        CitizenNumber citizenNumber = new CitizenNumber(rs.getString(6));
        PhoneNumber phoneNumber = new PhoneNumber(rs.getString(7));
        EmailAddress emailAddress = new EmailAddress(rs.getString(8));
        Gender gender = !(rs.getString(9).equals("Male")) ? !(rs.getString(9).equals("Female")) ? null : Gender.FEMALE : Gender.MALE;
        BloodType bloodType = BloodType.fromString(rs.getString(10));

        getMedicalMajorStatement.setInt(1, rs.getInt(11));
        ResultSet rs2 = getMedicalMajorStatement.executeQuery();
        MedicalMajor medicalMajor = new MedicalMajor(rs.getInt(11), rs2.getString(1));

        String time = rs.getString(12);
        ShiftHours shiftHours = new ShiftHours(
                LocalTime.of(Integer.valueOf(time.substring(0, 2)), Integer.valueOf(time.substring(3, 5))),
                LocalTime.of(Integer.valueOf(time.substring(6, 8)), Integer.valueOf(time.substring(9, 11))),
                time.length() > 12 ? LocalTime.of(Integer.valueOf(time.substring(15, 17)), Integer.valueOf(time.substring(18, 20))) : LocalTime.of(12, 0),
                time.length() > 12 ? LocalTime.of(Integer.valueOf(time.substring(21, 23)), Integer.valueOf(time.substring(24, 26))) : LocalTime.of(12, 0));

        return new Doctor(rs.getInt(1), rs.getString(2), rs.getString(3),
                rs.getString(4), date, citizenNumber, phoneNumber, emailAddress, gender, bloodType, medicalMajor, shiftHours);
    }

    public ArrayList<Treatment> treatments() {
        ArrayList<Treatment> treatments = new ArrayList<>();
        try {
            ResultSet rs = getTreatmentsStatement.executeQuery();
            while (rs.next()) {
                treatments.add(new Treatment(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return treatments;
    }

    public ArrayList<MedicalMajor> medicalMajors() {
        ArrayList<MedicalMajor> medicalMajors = new ArrayList<>();
        try {
            ResultSet rs = getMedicalMajorsStatement.executeQuery();
            while (rs.next()) {
                medicalMajors.add(new MedicalMajor(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicalMajors;
    }

    public ArrayList<Disease> diseases() {
        ArrayList<Disease> diseases = new ArrayList<>();
        try {
            ResultSet rs = getDiseasesStatement.executeQuery();
            while (rs.next()) {
                diseases.add(getDiseaseFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return diseases;
    }

    private Disease getDiseaseFromResultSet(ResultSet rs) throws SQLException {
        getMedicalMajorStatement.setInt(1, rs.getInt(3));
        ResultSet rs2 = getMedicalMajorStatement.executeQuery();
        MedicalMajor medicalMajor = new MedicalMajor(rs.getInt(3), rs2.getString(1));
        return new Disease(rs.getInt(1), rs.getString(2), medicalMajor);
    }
}
