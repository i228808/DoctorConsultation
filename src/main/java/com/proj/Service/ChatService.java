package com.proj.Service;

import com.proj.Bean.Chat;
import com.proj.Bean.Doctor;
import com.proj.Bean.Patient;
import com.proj.Repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    @Autowired
    private ChatRepository chatRepository;

    public Chat sendMessage(Doctor doctor, Patient patient, String message, String senderType) {
        Chat chat = new Chat();
        chat.setDoctor(doctor);
        chat.setPatient(patient);
        chat.setMessage(message);
        chat.setSenderType(senderType);
        return chatRepository.save(chat);
    }

    public List<Chat> getChatHistory(Doctor doctor, Patient patient) {
        return chatRepository.findByDoctorAndPatientOrderByTimestampDesc(doctor, patient);
    }

    public List<Chat> getUnreadMessages(Doctor doctor) {
        return chatRepository.findByDoctorAndIsReadFalseOrderByTimestampDesc(doctor);
    }

    public List<Chat> getUnreadMessages(Patient patient) {
        return chatRepository.findByPatientAndIsReadFalseOrderByTimestampDesc(patient);
    }

    public void markAsRead(List<Chat> messages) {
        messages.forEach(chat -> chat.setRead(true));
        chatRepository.saveAll(messages);
    }
}