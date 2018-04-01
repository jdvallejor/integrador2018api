package com.psl.integrador.config;

import com.psl.integrador.model.Collaborator;
import com.psl.integrador.model.Topic;
import com.psl.integrador.model.enums.Expertise;
import com.psl.integrador.repository.CollaboratorRepository;
import com.psl.integrador.repository.TopicRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.List;

@EnableMongoRepositories(basePackageClasses = {TopicRepository.class, CollaboratorRepository.class})
@Configuration
public class MongoDBConfig {

    @Bean
    CommandLineRunner commandLineRunner(TopicRepository topicRepository, CollaboratorRepository collaboratorRepository) {
        return strings -> {

            collaboratorRepository.deleteAll();
            topicRepository.deleteAll();

            List<Topic> listTopics = new ArrayList<>();
            Topic t1 = new Topic("Java", "Java test");
            t1.setTeachers(2);
            t1.setStudents(12);
            Topic t2 = new Topic("C#", "C# Test");
            t2.setTeachers(1);
            t2.setStudents(5);
            Topic t3 = new Topic("Python", "Python Test");
            t3.setTeachers(2);
            t3.setStudents(16);
            Topic t4 = new Topic("Ruby", "Ruby Test");
            t4.setTeachers(0);
            t4.setStudents(3);
            Topic t5 = new Topic("Scala", "Scala Test");
            t5.setTeachers(0);
            t5.setStudents(2);
            Topic t6 = new Topic("React.js", "React Test");
            t6.setTeachers(1);
            t6.setStudents(16);
            Topic t7 = new Topic("Angular 4", "Angular 4 Test");
            t7.setTeachers(2);
            t7.setStudents(8);
            Topic t8 = new Topic("MongoDB", "MongoDB Test");
            t8.setTeachers(1);
            t8.setStudents(5);
            Topic t9 = new Topic("Vue.js", "Vue Test");
            t9.setTeachers(1);
            t9.setStudents(4);
            Topic t10 = new Topic("SQL Server", "Scala Test");
            t10.setTeachers(0);
            t10.setStudents(2);
            Topic t11 = new Topic("Node.js", "Node Test");
            t11.setTeachers(3);
            t11.setStudents(11);
            Topic t12 = new Topic("Postgresql", "Postgresql Test");
            t12.setTeachers(0);
            t12.setStudents(1);
            Topic t13 = new Topic("Firebase", "Firebase Test");
            t13.setTeachers(0);
            t13.setStudents(4);
            Topic t14 = new Topic("Haskell", "Haskell Test");
            t14.setTeachers(0);
            t14.setStudents(15);
            Topic t15 = new Topic("Xamarin", "Xamarin Test");
            t15.setTeachers(1);
            t15.setStudents(3);
            Topic t16 = new Topic("R", "R test");
            t16.setTeachers(2);
            t16.setStudents(12);
            Topic t17 = new Topic("Groovy", "Groovy Test");
            t17.setTeachers(1);
            t17.setStudents(5);
            Topic t18 = new Topic("PowerShell", "PowerShell Test");
            t18.setTeachers(2);
            t18.setStudents(16);
            Topic t19 = new Topic("Perl", "Perl Test");
            t19.setTeachers(0);
            t19.setStudents(3);
            Topic t20 = new Topic("Kotlin", "Kotlin Test");
            t20.setTeachers(0);
            t20.setStudents(2);
            Topic t21 = new Topic("OCaml", "OCaml Test");
            t21.setTeachers(1);
            t21.setStudents(16);
            Topic t22 = new Topic("Matlab", "Matlab Test");
            t22.setTeachers(2);
            t22.setStudents(8);
            Topic t23 = new Topic("PureScript", "PureScript Test");
            t23.setTeachers(1);
            t23.setStudents(5);
            Topic t24 = new Topic("Fortran", "Fortran Test");
            t24.setTeachers(1);
            t24.setStudents(4);
            Topic t25 = new Topic("Gherkin", "Gherkin Test");
            t25.setTeachers(0);
            t25.setStudents(2);
            Topic t26 = new Topic("Assembly", "Assembly Test");
            t26.setTeachers(3);
            t26.setStudents(11);
            Topic t27 = new Topic("Crystal", "Crystal Test");
            t27.setTeachers(0);
            t27.setStudents(1);
            Topic t28 = new Topic("Swift", "Swift Test");
            t28.setTeachers(0);
            t28.setStudents(4);
            Topic t29 = new Topic("CoffeeScript", "CoffeeScript Test");
            t29.setTeachers(0);
            t29.setStudents(15);
            Topic t30 = new Topic("Groovy", "Groovy Test");
            t30.setTeachers(1);
            t30.setStudents(3);



            listTopics.add(t1);
            listTopics.add(t2);
            listTopics.add(t3);
            listTopics.add(t4);
            listTopics.add(t5);
            listTopics.add(t6);
            listTopics.add(t7);
            listTopics.add(t8);
            listTopics.add(t9);
            listTopics.add(t10);
            listTopics.add(t11);
            listTopics.add(t12);
            listTopics.add(t13);
            listTopics.add(t14);
            listTopics.add(t15);
            listTopics.add(t16);
            listTopics.add(t17);
            listTopics.add(t18);
            listTopics.add(t19);
            listTopics.add(t21);
            listTopics.add(t22);
            listTopics.add(t23);
            listTopics.add(t24);
            listTopics.add(t25);
            listTopics.add(t26);
            listTopics.add(t27);
            listTopics.add(t28);
            listTopics.add(t29);
            listTopics.add(t30);

            topicRepository.save(listTopics);

            Collaborator c1 = new Collaborator();
            c1.setName("Jose");
            c1.setEmail("josedavidrestrepoduque@gmail.com");
            c1.addTopicToTeach(t1, Expertise.beginner);
            c1.addTopicToLearn(t17, Expertise.expert);

            Collaborator c2 = new Collaborator();
            c2.setName("Juan");
            c2.setEmail("josedavidrestrepoduque@gmail.com");
            c2.addTopicToLearn(t1, Expertise.beginner);
            c2.addTopicToTeach(t18, Expertise.intermediate);
            c2.addTopicToLearn(t19, Expertise.expert);

            collaboratorRepository.save(c1);
            collaboratorRepository.save(c2);
        };
    }

}
