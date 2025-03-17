package com.librarymanager.config;

import com.librarymanager.model.Book;
import com.librarymanager.model.Role;
import com.librarymanager.model.User;
import com.librarymanager.repository.BookRepository;
import com.librarymanager.repository.RoleRepository;
import com.librarymanager.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, RoleRepository roleRepository, BookRepository bookRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (roleRepository.count() == 0) {
                Role adminRole = new Role();
                adminRole.setName("ADMIN");
                roleRepository.save(adminRole);

                Role librarianRole = new Role();
                librarianRole.setName("LIBRARIAN");
                roleRepository.save(librarianRole);

                Role readerRole = new Role();
                readerRole.setName("READER");
                roleRepository.save(readerRole);
            }

            if (userRepository.count() == 0) {
                Role adminRole = roleRepository.findByName("ADMIN");
                Role librarianRole = roleRepository.findByName("LIBRARIAN");
                Role readerRole = roleRepository.findByName("READER");

                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setEmail("admin@example.com");
                Set<Role> adminRoles = new HashSet<>();
                adminRoles.add(adminRole);
                admin.setRoles(adminRoles);
                userRepository.save(admin);

                User librarian = new User();
                librarian.setUsername("librarian");
                librarian.setPassword(passwordEncoder.encode("librarian"));
                librarian.setEmail("librarian@example.com");
                Set<Role> librarianRoles = new HashSet<>();
                librarianRoles.add(librarianRole);
                librarian.setRoles(librarianRoles);
                userRepository.save(librarian);

                User reader = new User();
                reader.setUsername("reader");
                reader.setPassword(passwordEncoder.encode("reader"));
                reader.setEmail("reader@example.com");
                Set<Role> readerRoles = new HashSet<>();
                readerRoles.add(readerRole);
                reader.setRoles(readerRoles);
                userRepository.save(reader);
            }

            if (bookRepository.count() == 0) {
                Book book1 = new Book();
                book1.setTitle("The Great Gatsby");
                book1.setAuthor("F. Scott Fitzgerald");
                book1.setPublishedDate(new Date());
                book1.setAvailable(true);
                bookRepository.save(book1);

                Book book2 = new Book();
                book2.setTitle("1984");
                book2.setAuthor("George Orwell");
                book2.setPublishedDate(new Date());
                book2.setAvailable(true);
                bookRepository.save(book2);

                Book book3 = new Book();
                book3.setTitle("To Kill a Mockingbird");
                book3.setAuthor("Harper Lee");
                book3.setPublishedDate(new Date());
                book3.setAvailable(true);
                bookRepository.save(book3);
            }
        };
    }
}