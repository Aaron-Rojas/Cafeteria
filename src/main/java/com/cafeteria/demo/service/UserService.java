package com.cafeteria.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cafeteria.demo.model.User;

@Service
public class UserService {

      private List<User> usuarios = new ArrayList<>();

      public UserService(){
        usuarios.add(new User("Admin01@cafeteria.com", "Admin", "1234")); 
        usuarios.add(new User("cliente1@mail.com", "cliente1", "pass123"));
      }

    
         // Método para guardar un nuevo usuario
        public void saveUser(User user) {
        // Opcional: podrías añadir validación aquí (email no nulo/vacío, etc.)
        if (user != null && user.getEmail() != null && !user.getEmail().trim().isEmpty()) {
            this.usuarios.add(user);
            System.out.println("Usuario registrado: " + user.getUsername() + " (" + user.getEmail() + ")");
             // Opcional: loguear el estado actual de la lista de usuarios
             System.out.println("Total de usuarios en lista: " + this.usuarios.size());
        } else {
             System.out.println("Intento de registrar usuario nulo o con email vacío.");
        }
    }
     
    // Método para verificar si un usuario existe (por email o username)
    // Usaremos el email por ahora, ya que debe ser único
    public boolean existeUsuario(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false; // Un email vacío no puede existir
        }
        String lowerCaseEmail = email.trim().toLowerCase();
        return usuarios.stream().anyMatch(user ->
            // Verificamos que el objeto user no sea null Y que user.getEmail() no sea null
            user != null && user.getEmail() != null && user.getEmail().toLowerCase().equals(lowerCaseEmail)
        );
    }
    
    

         // Método para encontrar un usuario por email y contraseña (para el login)
    public User findUserByEmailAndPassword(String email, String password) {
         if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return null; // No se puede buscar con credenciales vacías
        }
        String lowerCaseEmail = email.trim().toLowerCase();
        String trimmedPassword = password.trim();

        Optional<User> foundUserOptional = usuarios.stream()
            .filter(user ->
                 // Verificar nulls antes de acceder a propiedades y comparar
                 user != null &&
                 user.getEmail() != null && user.getEmail().toLowerCase().equals(lowerCaseEmail) &&
                 user.getPassword() != null && user.getPassword().equals(trimmedPassword)
            )
            .findFirst(); // Devolver el primer usuario que coincida (el email debería ser único)

        return foundUserOptional.orElse(null); // Devuelve el usuario si se encontró, o null si no
    }
    
        // --- Otros métodos si los necesitas (ej: listar usuarios, etc.) ---
        public List<User> getAllUsers() {
        return new ArrayList<>(usuarios); // Retorna una copia
    }
}

