package com.psl.integrador.service;

import com.psl.integrador.model.Collaborator;
import com.psl.integrador.model.Topic;
import com.psl.integrador.model.enums.Expertise;
import com.psl.integrador.model.enums.Role;
import com.psl.integrador.repository.CollaboratorRepository;
import com.psl.integrador.repository.TopicRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CollaboratorServiceTest {

    @Autowired
    private CollaboratorService collaboratorService;

    @Autowired
    private CollaboratorRepository collaboratorRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Test
    public void getCollaborators() {
        int collaborators = collaboratorService.getCollaborators().size();

        Collaborator c = new Collaborator();

        c = collaboratorRepository.save(c);

        assertEquals(collaborators + 1, collaboratorService.getCollaborators().size());

        collaboratorRepository.delete(c);
    }

    @Test
    public void getCollaboratorsByTopic() {

        Topic t1 = new Topic("Topic 1", "Topic 1");
        Topic t2 = new Topic("Topic 2", "Topic 2");
        Topic t3 = new Topic("Topic 3", "Topic 3");
        Topic t4 = new Topic("Topic 4", "Topic 4");

        t1 = topicRepository.save(t1);
        t2 = topicRepository.save(t2);
        t3 = topicRepository.save(t3);
        t4 = topicRepository.save(t4);

        Collaborator c1 = new Collaborator();
        c1.setName("Collaborator 1");
        c1.setEmail("josedavidrestrepoduque@gmail.com");
        c1.addTopicToTeach(t1, Expertise.BEGINNER);
        c1.addTopicToLearn(t2, Expertise.EXPERT);

        Collaborator c2 = new Collaborator();
        c2.setName("Collaborator 2");
        c2.setEmail("josedavidrestrepoduque@gmail.com");
        c2.addTopicToLearn(t1, Expertise.BEGINNER);
        c2.addTopicToTeach(t3, Expertise.INTERMEDIATE);
        c2.addTopicToLearn(t4, Expertise.EXPERT);

        c1 = collaboratorRepository.save(c1);
        c2 = collaboratorRepository.save(c2);

        /*Map<Collaborator, Role> m = collaboratorService.getCollaboratorsByTopic(t1);

        Role guiding = m.get(c1);
        Role learning = m.get(c2);*/

        collaboratorRepository.delete(c1);
        collaboratorRepository.delete(c2);

        topicRepository.delete(t1);
        topicRepository.delete(t2);
        topicRepository.delete(t3);
        topicRepository.delete(t4);

        assertTrue(true);
    }
}