package ba.unsa.etf.rpr.projekat;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

public class Patient extends Person { // some methods may be deleted later
    private Height height;
    private Weight weight;
    private List<Allergy> allergies;

    public static class Height {
        private int height;

        public Height(int height) {
            if (height < 50 || height > 250) throw new InvalidInformationException("Invalid height");
            this.height = height;
        }

        public Height(double height) {
            if (height < 0.5 || height > 2.5) throw new InvalidInformationException("Invalid height");
            this.height = (int) (100 * height);
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            if (height < 0.5 || height > 2.5) throw new InvalidInformationException("Invalid height");
            this.height = height;
        }

        @Override
        public String toString() {
            return height + "cm";
        }
    }

    public static class Weight {
        private double weight;

        public Weight(double weight) {
            if (weight < 5 || weight > 500) throw new InvalidInformationException("Invalid weight");
            this.weight = weight;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            if (weight < 5 || weight > 500) throw new InvalidInformationException("Invalid weight");
            this.weight = weight;
        }

        @Override
        public String toString() {
            return new DecimalFormat("#.#").format(weight) + "kg";
        }
    }

    public static class Allergy {
        private int id;
        private String allergyName;

        public Allergy(int id, String allergyName) {
            this.id = id;
            this.allergyName = allergyName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAllergyName() {
            return allergyName;
        }

        public void setAllergyName(String allergyName) {
            this.allergyName = allergyName;
        }

        @Override
        public String toString() {
            return allergyName;
        }
    }

    public Patient(int id, String firstName, String lastName, String homeAddress, LocalDate birthDate,
                   CitizenNumber citizenNumber, PhoneNumber phoneNumber, EmailAddress emailAddress, Gender gender,
                   BloodType bloodType, Height height, Weight weight, List<Allergy> allergies) {

        super(id, firstName, lastName, homeAddress, birthDate, citizenNumber, phoneNumber, emailAddress, gender,
                bloodType);
        this.height = height;
        this.weight = weight;
        this.allergies = allergies;
    }

    public Height getHeight() {
        return height;
    }

    public void setHeight(Height height) {
        this.height = height;
    }

    public Weight getWeight() {
        return weight;
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    public double getBMI() {
        return Math.round(weight.weight / (height.height * height.height / 10000.0) * 10) / 10.0;
    }

    public List<Allergy> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<Allergy> allergies) {
        this.allergies = allergies;
    }

    public void addAllergy(Allergy allergy) {
        allergies.add(allergy);
    }

    @Override
    public String toString() {
        return "Patient{" + "\n" +
                " height = " + height + "\n" +
                " weight = " + weight + "\n" +
                " BMI = " + getBMI() + "\n" +
                " allergies = " + allergies + "\n" +
                " id = " + id + "\n" +
                " firstName = '" + firstName + '\'' + "\n" +
                " lastName = '" + lastName + '\'' + "\n" +
                " homeAddress = '" + homeAddress + '\'' + "\n" +
                " birthDate = " + birthDate + "\n" +
                " citizenNumber = " + citizenNumber + "\n" +
                " phoneNumber = " + phoneNumber + "\n" +
                " emailAddress = " + emailAddress + "\n" +
                " gender = " + gender + "\n" +
                " bloodType = " + bloodType + "\n" +
                '}' + "\n";
    }
}
