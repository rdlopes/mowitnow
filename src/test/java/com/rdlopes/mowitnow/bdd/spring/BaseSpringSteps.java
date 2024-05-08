package com.rdlopes.mowitnow.bdd.spring;

import com.rdlopes.mowitnow.MowItNowApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = MowItNowApplication.class)
public class BaseSpringSteps {

}
