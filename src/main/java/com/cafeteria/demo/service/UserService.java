package com.cafeteria.demo.service;

import com.cafeteria.demo.model.User;
import com.cafeteria.demo.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importación correcta para @Transactional

import java.util.List;
import java.util.Optional; // Importación necesaria para Optional

@Service
public class UserService {

    private final UsuarioRepository usuarirepo;

    @Autowired // Inyección de dependencias por constructor (práctica recomendada)
    public UserService(UsuarioRepository usuarirepo) {
        this.usuarirepo = usuarirepo;
    }

    // --- Métodos de LECTURA (READ del CRUD) ---

    @Transactional(readOnly = true) // Indica que esta operación es solo de lectura
    public List<User> listausuarios() {
        System.out.println("--- DEBUG UserService: Listando todos los usuarios ---");
        // Utiliza el método findAll() de JpaRepository para obtener todos los usuarios
        return usuarirepo.findAll();
    }

    @Transactional(readOnly = true)
    public List<User> buscarusuariopornombre(String nombreusuario) {
        System.out.println("--- DEBUG UserService: Buscando usuarios por nombre: " + nombreusuario + " ---");
        // Utiliza el método derivado de JpaRepository: findByUsernameContainingIgnoreCase
        return usuarirepo.findByUsernameContainingIgnoreCase(nombreusuario);
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        System.out.println("--- DEBUG UserService: Buscando usuario por ID: " + id + " ---");
        // Utiliza el método findById() de JpaRepository
        return usuarirepo.findById(id);
    }

    // Método para buscar un usuario por su email (necesario para el login y registro)
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        System.out.println("--- DEBUG UserService: Buscando usuario por email: " + email + " ---");
        // Llama al método findByEmail definido en UsuarioRepository
        return usuarirepo.findByEmail(email);
    }

    // --- Métodos de ESCRITURA (CREATE, UPDATE, DELETE del CRUD) usando JPA estándar ---

    @Transactional // Indica que esta operación es transaccional (modifica la base de datos)
    public void registrausuarios(User usuario) {
        System.out.println("--- DEBUG UserService: Intentando registrar usuario: " + usuario.getEmail() + " ---");
        // Primero, verifica si ya existe un usuario con el mismo email para evitar duplicados
        if (usuarirepo.findByEmail(usuario.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con este email: " + usuario.getEmail());
        }

        // Asigna el rol ID basado en el email
        Long rolId;
        if (usuario.getEmail().toLowerCase().startsWith("admin")) {
            rolId = 1L; // Asigna rol de administrador
            System.out.println("--- DEBUG UserService: Asignando rol ADMIN (ID: 1) ---");
        } else {
            rolId = 2L; // Asigna rol de cliente por defecto
            System.out.println("--- DEBUG UserService: Asignando rol CLIENTE (ID: 2) ---");
        }
        usuario.setRolusuario(rolId); // Establece el rol ID en el objeto User

        // Guarda el nuevo usuario en la base de datos.
        // Si usuario.usuarioid es null, save() insertará un nuevo registro.
        usuarirepo.save(usuario);
        System.out.println("--- DEBUG UserService: Usuario " + usuario.getEmail() + " registrado usando JPA save() ---");
    }

    @Transactional
    public void modificarusuarios(User usuario) {
        System.out.println("--- DEBUG UserService: Intentando modificar usuario con ID: " + usuario.getUsuarioid() + " ---");
        // Busca el usuario existente en la base de datos para asegurar que exista y obtener su estado actual
        Optional<User> existingUserOptional = usuarirepo.findById(usuario.getUsuarioid());
        
        if (!existingUserOptional.isPresent()) {
            throw new IllegalArgumentException("Usuario no encontrado para actualizar con ID: " + usuario.getUsuarioid());
        }
        
        User existingUser = existingUserOptional.get();
        
        // Actualiza solo los campos permitidos desde el formulario de edición
        existingUser.setUsername(usuario.getUsername()); // Actualiza el nombre de usuario
        existingUser.setEmail(usuario.getEmail());     // Actualiza el email

        // Nota: La contraseña y el rol no se actualizan directamente desde este método por razones de seguridad
        // y lógica de negocio. Se manejarían en métodos separados o a través de un controlador de seguridad.

        // Guarda las modificaciones del usuario existente.
        // save() actualiza el registro si el objeto tiene un ID existente.
        usuarirepo.save(existingUser);
        System.out.println("--- DEBUG UserService: Usuario con ID " + usuario.getUsuarioid() + " modificado usando JPA save() ---");
    }

    @Transactional
    public void borrarusuarioporid(Long id) {
        System.out.println("--- DEBUG UserService: Intentando borrar usuario con ID: " + id + " ---");
        // Utiliza el método deleteById() de JpaRepository
        // JPA maneja las restricciones de clave foránea si están configuradas (ON DELETE CASCADE, etc.)
        // o lanzará una excepción si hay registros dependientes sin un manejo adecuado.
        usuarirepo.deleteById(id);
        System.out.println("--- DEBUG UserService: Usuario con ID " + id + " borrado ---");
    }
}
    // El método modificarcontrasena no se implementa ni usa por ahora, según lo acordado.
    // @Transactional
    // public void modificarcontrasena(String nombreusuario, String contrasena) {
    //    // Implementación si se activa en el futuro, por ejemplo, usando usuarirepo.findByUsername y luego save(user)
    // }