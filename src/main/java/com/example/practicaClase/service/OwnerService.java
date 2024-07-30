package com.example.practicaClase.service;


import com.example.practicaClase.persintence.entities.Owner;
import com.example.practicaClase.persintence.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OwnerService {


    @Autowired
    OwnerRepository ownerRepository;

    public void newOwner(Owner owner){ownerRepository.save(owner);}

}
