package com.example.techstore.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.techstore.dto.ClienteDTO;
import com.example.techstore.exception.EmailDuplicadoException;
import com.example.techstore.model.Cliente;
import com.example.techstore.repository.ClienteRepository;

@Service 
public class ClienteService {
    
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente crearCliente(ClienteDTO clienteDTO) {

        Cliente cliente = new Cliente();

        cliente.setNombre(clienteDTO.getNombre());
        cliente.setApellido(clienteDTO.getApellido());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setFechaRegistro(LocalDateTime.now());

        return clienteRepository.save(cliente);
    }

    public Cliente crearClienteValidado(ClienteDTO clienteDTO) {

        if (clienteRepository.existsByEmail(clienteDTO.getEmail())) {
            throw new EmailDuplicadoException("El email ya est registrado");
        }

        Cliente cliente = new Cliente();

        cliente.setNombre(clienteDTO.getNombre());
        cliente.setApellido(clienteDTO.getApellido());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setFechaRegistro(LocalDateTime.now());

        return clienteRepository.save(cliente);
    }
}

