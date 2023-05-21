package com.example.demo.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public List<BmiUser> getUsers() {
        return userRepository.findAll();
    }



    public BmiUser addNewBmiUser(BmiUser user)  {

        if (user.getWeight() <= 0 ) {
            user.setWeight(60.0);
        }
        if (user.getHeight() <= 0 ) {
            user.setHeight(1.7);
        }
        double bmi = user.getWeight() / (user.getHeight() * user.getHeight());
        user.setBmi_result(bmi);
        BmiUser savedUser = new BmiUser();
            if (user.getBmi_result() < 16) {
                user.setBmi_type("Severe Thinnes");
            } else if (user.getBmi_result() >= 16 && user.getBmi_result() < 17) {
                user.setBmi_type("Moderate Thinnes");
            } else if (user.getBmi_result() >= 17 && user.getBmi_result() < 18.5) {
                user.setBmi_type("Mild Thinnes");
            } else if (user.getBmi_result() >= 18.5 && user.getBmi_result() < 25) {
                user.setBmi_type("Normal");
            } else if (user.getBmi_result() >= 25 && user.getBmi_result() < 30) {
                user.setBmi_type("Overweight");
            } else if (user.getBmi_result() >= 30 && user.getBmi_result() < 35) {
                user.setBmi_type("Obese Class 1");
            } else if (user.getBmi_result() >= 35 && user.getBmi_result() < 40) {
                user.setBmi_type("Obese Class 2");
            } else if (user.getBmi_result() >= 40) {
                user.setBmi_type("Obese Class 3");
            }

            savedUser = userRepository.save(user);
        return savedUser;
    }

    public void deleteBmiUser(Integer id) {
        boolean exists = userRepository.existsById(id);

        if (exists) {
            userRepository.deleteById(id);
            System.out.println("deleted");
        }
    }

    public Optional<BmiUser> getUserById(Integer id) {
        boolean exists = userRepository.existsById(id);

        if (!exists) {
            System.out.println("can not find the user with this id...");
            return null;
        }

        return userRepository.findById(id);
    }


    public List<BmiUser> getDataFromNView() {
        return userRepository.findAllFromNView();
    }

    public List<BmiUser> getUsersByAscedingId(){
        return userRepository.findAllAscendingId();
    }




    public void updateUser(BmiUser updatedUser, Integer id) {
        Optional<BmiUser> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            BmiUser user = userOptional.get();

            // Update the user object with new information
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setHeight(updatedUser.getHeight());
            user.setWeight(updatedUser.getWeight());

            // Calculate the new BMI and set the BMI type
            double bmi = user.getWeight() / (user.getHeight() * user.getHeight());
            user.setBmi_result(bmi);

            if (bmi < 16) {
                user.setBmi_type("Severe Thinnes");
            } else if (bmi >= 16 && bmi < 17) {
                user.setBmi_type("Moderate Thinnes");
            } else if (bmi >= 17 && bmi < 18.5) {
                user.setBmi_type("Mild Thinnes");
            } else if (bmi >= 18.5 && bmi < 25) {
                user.setBmi_type("Normal");
            } else if (bmi >= 25 && bmi < 30) {
                user.setBmi_type("Overweight");
            } else if (bmi >= 30 && bmi < 35) {
                user.setBmi_type("Obese Class 1");
            } else if (bmi >= 35 && bmi < 40) {
                user.setBmi_type("Obese Class 2");
            } else if (bmi >= 40) {
                user.setBmi_type("Obese Class 3");
            }

            // Save the updated user object back to the database
            userRepository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }


    public List<BmiUser> getUsersByName() {
        return userRepository.filterByName();
    }
}

    /* public DataSource createDataSource(String url , String username, String password){

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    } */



