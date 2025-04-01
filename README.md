# Doctor Consultation Program

A modern web application that facilitates seamless communication and management between doctors and patients. Built with Spring Boot and featuring a beautiful, responsive UI.

## 🌟 Features

### For Patients

- **Appointment Management**

  - Schedule appointments with doctors
  - View and manage upcoming appointments
  - Cancel appointments with reason tracking
  - View appointment history

- **Health Records**

  - Access medical history
  - View prescriptions
  - Track health goals
  - Manage personal health information

- **Doctor Search**
  - Browse doctors by specialization
  - View doctor profiles and ratings
  - Read doctor reviews and feedback

### For Doctors

- **Patient Management**

  - View patient profiles
  - Add medical records
  - Create and manage prescriptions
  - Set health goals for patients

- **Appointment Management**

  - View and manage patient appointments
  - Track appointment history
  - Handle appointment cancellations

- **Profile Management**
  - Maintain professional profile
  - Update availability
  - Manage specialization and bio

## 🛠️ Technology Stack

- **Backend**

  - Spring Boot 3.x
  - Spring Data JPA
  - MySQL Database
  - Spring Security

- **Frontend**
  - Thymeleaf Templates
  - Tailwind CSS
  - JavaScript
  - Font Awesome Icons

## 📋 Prerequisites

- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

## 🚀 Getting Started

1. **Clone the Repository**

   ```bash
   git clone https://github.com/yourusername/doctor-consultation-program.git
   cd doctor-consultation-program
   ```

2. **Configure Database**

   - Create a MySQL database named `doctor_consultation`
   - Update `application.properties` with your database credentials

3. **Build the Application**

   ```bash
   mvn clean install
   ```

4. **Run the Application**

   ```bash
   mvn spring-boot:run
   ```

5. **Access the Application**
   - Open your browser and navigate to `http://localhost:8080`
   - Register as a doctor or patient
   - Start using the application

## 📁 Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/proj/
│   │       ├── Bean/          # Entity classes
│   │       ├── Controller/    # REST controllers
│   │       ├── Repository/    # JPA repositories
│   │       ├── Service/       # Business logic
│   │       └── Config/        # Configuration classes
│   └── resources/
│       ├── templates/         # Thymeleaf templates
│       ├── static/           # Static resources
│       └── application.properties
```

## 🔒 Security Features

- Role-based authentication (Doctor/Patient)
- Secure password hashing
- Session management
- Protected endpoints

## 🎨 UI/UX Features

- Responsive design for all devices
- Dark mode support
- Interactive modals
- Real-time notifications
- Beautiful animations and transitions

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors

- Abdullah Mansoor - Initial work

## 🙏 Acknowledgments

- Spring Boot team for the amazing framework
- Tailwind CSS for the beautiful styling
- Font Awesome for the icons
- All contributors who have helped improve this project

## 📞 Support

For support, email i228808@nu.edu.pk or create an issue in the repository.

---

Made with ❤️ for better healthcare management
