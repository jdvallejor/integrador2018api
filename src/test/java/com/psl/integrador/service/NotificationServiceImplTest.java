package com.psl.integrador.service;

import com.psl.integrador.model.Collaborator;
import com.psl.integrador.model.Topic;
import com.psl.integrador.model.enums.Expertise;
import com.psl.integrador.model.enums.Role;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NotificationServiceImplTest {

    @TestConfiguration
    static class NotificationServiceImplTestContextConfiguration {
        @Bean
        public NotificationService notificationService() {
            return new NotificationServiceImpl();
        }
    }

    @Autowired
    private NotificationService notificationService;

    @MockBean
    private CollaboratorService collaboratorService;

    @Autowired
    private TopicService topicService;

    @Before
    public void setUp() {
        Map<Collaborator, Role> collaborators = new HashMap<>();
        Collaborator collaborator = new Collaborator();
        collaborator.setEmail("col1@samplemail.com");
        collaborators.put(collaborator,Role.student);
        collaborator = new Collaborator();
        collaborator.setEmail("col2@samplemail.com");
        collaborators.put(collaborator,Role.teacher);

        Mockito.when(collaboratorService.getCollaboratorsByTopic(new Topic()))
                .thenReturn(collaborators);
    }

    @Test
    public void sendNotification() {

        Topic topic = new Topic("Topic","Test topic");
        Collaborator[] collaborator = new Collaborator[5];
        for(int c = 0; c < 5; ++c){
            collaborator[c] = new Collaborator();
            collaborator[c].setEmail(c + "@mail.com");
            collaborator[c].addTopicToLearn(topic, Expertise.beginner);
        }
        //Void function, test is only to verify there is no crash
        notificationService.sendNotification(collaboratorService.getCollaboratorsByTopic(new Topic()),topic,1);
        assertTrue(true);
    }

}
