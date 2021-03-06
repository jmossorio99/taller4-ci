package com.ossorio.barrera.taller4.delegate.implementation;

import com.ossorio.barrera.taller4.delegate.interfaces.SymptomquestionDelegate;
import com.ossorio.barrera.taller4.model.Symptomquestion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class SymptomquestionDelegateImp implements SymptomquestionDelegate {

    private final String SERVER = "http://localhost:8080/questions/";
    RestTemplate restTemplate;

    public SymptomquestionDelegateImp(){
        this.restTemplate = new RestTemplate();
    }

    @Override
    public List<Symptomquestion> findAll() {
        try{
            return Arrays.asList(restTemplate.getForObject(SERVER, Symptomquestion[].class));
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Symptomquestion findById(Long id) {
        return restTemplate.getForObject(SERVER + id, Symptomquestion.class);
    }

    @Override
    public Symptomquestion save(Symptomquestion symptomquestion) {
        return restTemplate.postForEntity(SERVER, symptomquestion, Symptomquestion.class).getBody();
    }

    @Override
    public Symptomquestion update(Symptomquestion symptomquestion) {
        restTemplate.put(SERVER, symptomquestion, Symptomquestion.class);
        return symptomquestion;
    }

    @Override
    public void delete(Symptomquestion symptomquestion) {
        restTemplate.delete(SERVER, symptomquestion, Symptomquestion.class);
    }
}
