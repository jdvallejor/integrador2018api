package com.psl.integrador.service;

import com.psl.integrador.model.Collaborator;
import com.psl.integrador.model.Topic;
import com.psl.integrador.model.enums.NotificationType;
import com.psl.integrador.model.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger LOGGER = Logger.getLogger(NotificationServiceImpl.class.getName());

    private static final String FROM_EMAIL = "praxistestmailintegrator2018@gmail.com";
    private static final String PASSWORD = "Praxis2018";

    private static final String SUBJECT_OPENED = "Nuevo grupo de %topic%!";
    private static final String SUBJECT_CLOSED = "El grupo de %topic% se ha cerrado";

    private static final String BODY_LEARNING =
            "Hola %name%" +
                    "\n\nHace un tiempo nos comentaste tu interés en aprender %topic%, hemos creado un grupo en Skype " +
                    "para que compartas información con otras personas que también tienen interes en este tema." +
                    "\n\nAquí puedes acceder al grupo: %chat%" +
                    "\n\nAdicionalmente, estos compañeros te podrán proporcionar guía: " +
                    "\n\nSi tienes alguna duda escribenos a: %mail%" +
                    "\n\nLogistica actividades PSL";

    private static final String BODY_GUIDING =
            "Hola %name%" +
                    "\n\nHace un tiempo nos comentaste tu interés en guiar en el tema %topic%, hemos creado un grupo en Skype " +
                    "para que ayudes a compañeros que tienen interés de aprender sobre este tema." +
                    "\n\nAquí puedes acceder al grupo: %chat%" +
                    "\n\nSi tienes alguna duda escribenos a: %mail%" +
                    "\n\nLogistica actividades PSL";

    private static final String BODY_NEVER_OPENED =
            "Hola %name%" +
                    "\n\nHace un tiempo nos comentaste tu interés en el tema %topic%, lamentablemente no encontramos el número " +
                    "suficiente de personas para abrir el grupo. No te desanimes! Te invitamos a inscribirte a otros grupos ya activos." +
                    "\n\nSi tienes alguna duda escribenos a: %mail%" +
                    "\n\nLogistica actividades PSL";

    private static final String BODY_CLOSED =
            "Hola %name%" +
                    "\n\nQueremos informarte que el grupo %topic% se ha cerrado, si consideras que debería reactivarse, escríbenos a: %mail%." +
                    "\n\nTe invitamos a inscribirte a otros grupos activos" +
                    "\n\nLogistica actividades PSL";
    private final CollaboratorService collaboratorService;
    private Authenticator auth = new Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
        }
    };

    @Autowired
    public NotificationServiceImpl(CollaboratorService collaboratorService) {
        this.collaboratorService = collaboratorService;
    }

    private String buildSubject(String topicName, NotificationType notificationType) {
        switch (notificationType) {
            case open:
                return SUBJECT_OPENED.replace("%topic%", topicName);
            case closed:
            case neverOpened:
                return SUBJECT_CLOSED.replace("%topic%", topicName);
            default:
                return null;
        }
    }

    private String buildBody(String name, String topicName, String chat, Role role, NotificationType notificationType) {
        switch (notificationType) {
            case open:
                switch (role) {
                    case student:
                        return BODY_LEARNING.replace("%name%", name)
                                .replace("%topic%", topicName)
                                .replace("%chat%", chat)
                                .replace("%mail%", FROM_EMAIL);
                    case teacher:
                        return BODY_GUIDING.replace("%name%", name)
                                .replace("%topic%", topicName)
                                .replace("%chat%", chat)
                                .replace("%mail%", FROM_EMAIL);
                }
            case neverOpened:
                return BODY_NEVER_OPENED.replace("%name%", name)
                        .replace("%topic%", topicName)
                        .replace("%mail%", FROM_EMAIL);
            case closed:
                return BODY_CLOSED.replace("%name%", name)
                        .replace("%topic%", topicName)
                        .replace("%mail%", FROM_EMAIL);
        }
        return null;
    }

    private CustomMessage buildMessage(Collaborator collaborator, Topic topic, Role role, NotificationType notificationType) {

        CustomMessage message = new CustomMessage();

        message.setToEmail(collaborator.getEmail());
        message.setSubject(buildSubject(topic.getName(), notificationType));
        message.setBody(buildBody(collaborator.getName(), topic.getName(), topic.getChat(), role, notificationType));

        return message;
    }

    private Session getSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return Session.getInstance(props, auth);
    }

    private MimeMessage buildEmail(Session session, CustomMessage message) throws MessagingException, UnsupportedEncodingException {
        MimeMessage msg = new MimeMessage(session);

        msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
        msg.addHeader("format", "flowed");
        msg.addHeader("Content-Transfer-Encoding", "8bit");
        msg.setFrom(new InternetAddress("praxistestmailintegrator2018@gmail.com", "NoReply-JD"));
        msg.setReplyTo(InternetAddress.parse("praxistestmailintegrator2018@gmail.com", false));

        msg.setSubject(message.getSubject(), "UTF-8");
        msg.setText(message.getBody(), "UTF-8");

        msg.setSentDate(new Date());
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(message.getToEmail(), false));

        return msg;
    }

    private void sendEmail(Session session, CustomMessage message) {
        try {

            MimeMessage msg = buildEmail(session, message);

            LOGGER.log(Level.INFO, "Message is ready");
            Transport.send(msg);
            LOGGER.log(Level.INFO, "EMail Sent Successfully!!");

        } catch (MessagingException | UnsupportedEncodingException e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }
    }

    @Override
    public void sendNotifications(Topic topic, NotificationType notificationType) {

        for (Map.Entry<Collaborator, Role> entry : collaboratorService.getCollaboratorsByTopic(topic).entrySet()) {

            Collaborator collaborator = entry.getKey();
            Role role = entry.getValue();

            sendEmail(getSession(), buildMessage(collaborator, topic, role, notificationType));

            LOGGER.log(Level.INFO, "Sending email to " + collaborator.getName() + " " + collaborator.getEmail() + " " + role);
        }
    }


    private class CustomMessage {
        private String toEmail;
        private String subject;
        private String body;

        String getToEmail() {
            return toEmail;
        }

        void setToEmail(String toEmail) {
            this.toEmail = toEmail;
        }

        String getSubject() {
            return subject;
        }

        void setSubject(String subject) {
            this.subject = subject;
        }

        String getBody() {
            return body;
        }

        void setBody(String body) {
            this.body = body;
        }
    }

}
