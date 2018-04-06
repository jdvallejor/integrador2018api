package com.psl.integrador.service;

import com.psl.integrador.exception.EntityNotFoundException;
import com.psl.integrador.model.Topic;
import com.psl.integrador.repository.TopicRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.psl.integrador.model.enums.Status.OPENED;
import static com.psl.integrador.model.enums.Status.TO_OPEN;
import static com.psl.integrador.model.enums.Status.CLOSED;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TopicServiceTest {

    @Autowired
    private TopicService topicService;

    @Autowired
    private TopicRepository topicRepository;



    @After
    public void tearDown() {
        topicRepository.deleteAll();
    }


    @Test
    public void getTopicsByStatus() {
        Topic t1 = new Topic();
        t1.setName("Topic 1");
        t1.setDescription("Description 1");
        t1.setStatus(TO_OPEN);
        topicService.add(t1);

        Topic t2 = new Topic();
        t2.setName("Topic 2");
        t2.setDescription("Description 2");
        t2.setStatus(OPENED);
        topicService.add(t2);

        Topic t3 = new Topic();
        t3.setName("Topic 3");
        t3.setDescription("Description 3");
        t3.setStatus(CLOSED);
        topicService.add(t3);

        Topic t4 = new Topic();
        t4.setName("Topic 4");
        t4.setDescription("Description 4");
        t4.setStatus(CLOSED);
        topicService.add(t4);

        assertEquals(2, topicService.getTopicsByStatus(CLOSED.ordinal()).size());
       // assertEquals(1, topicService.getTopicsByStatus(OPENED.ordinal()).size());
       // assertEquals(1, topicService.getTopicsByStatus(TO_OPEN.ordinal()).size());
    }

    @Test
    public void add() {
        Topic t1 = new Topic();
        t1.setName("Topic");
        t1.setDescription("Description");
        t1.setStatus(TO_OPEN);

        assertThat(t1, samePropertyValuesAs(topicService.add(t1)));
    }

    @Test
    public void update() {
        Topic t1 = new Topic();
        t1.setName("Topic 1");
        t1.setDescription("Description 1");
        t1.setStatus(TO_OPEN);
        Topic t2 = topicService.add(t1);
        t2.setName("Topic 1");
        t2.setDescription("Description 1");
        t2.setStatus(TO_OPEN);

        try {
            assertThat(t2, samePropertyValuesAs(topicService.update(t2)));
        }catch (EntityNotFoundException ex){
            fail(ex.getMessage());
        }
    }

    @Test
    public void getTopicById() {
        Topic t1 = new Topic();
        t1.setName("Topic");
        t1.setDescription("Description");
        t1.setStatus(TO_OPEN);
        Topic t2 = topicService.add(t1);

        assertThat(t2, samePropertyValuesAs(topicService.getTopicById(t2.getId())));
    }
}
