package com.cafeteria.demo.service;

import com.cafeteria.demo.model.Mesa;
import com.cafeteria.demo.repository.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import java.util.List;

@Service
public class MesaService {

    private final MesaRepository mesaRepository;
    @Autowired // Inyección por constructor
    public MesaService(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }


    @Transactional(readOnly = true)
    public List<Mesa> obtenerTodasLasMesas() {
        return mesaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional <Mesa> obtenerMesaPorId(Long id) {
        return mesaRepository.findById(id);
    }

    @Transactional
    public Mesa guardarMesa(Mesa mesa) { // Cambiar nombre a 'guardarMesa' y retornar la mesa guardada
        return mesaRepository.save(mesa);
    }

    @Transactional
    public void eliminarMesa(Long id) { // Añadir método de eliminación
    mesaRepository.deleteById(id);
    }
}