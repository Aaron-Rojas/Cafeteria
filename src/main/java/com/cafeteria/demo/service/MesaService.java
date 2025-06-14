package com.cafeteria.demo.service;

import com.cafeteria.demo.model.Mesa;
import com.cafeteria.demo.repository.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MesaService {

    @Autowired
    private MesaRepository mesaRepository;

    @Transactional(readOnly = true)
    public List<Mesa> obtenerTodasLasMesas() {
        return mesaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Mesa obtenerMesaPorId(Long id) {
        return mesaRepository.findById(id).orElse(null);
    }

    @Transactional
    public void actualizarMesa(Mesa mesa) {
        mesaRepository.save(mesa);
    }
}
