package com.kotlin.demo

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@SpringBootApplication
class KotlinDemoApplication

fun main(args: Array<String>) {
    SpringApplication.run(KotlinDemoApplication::class.java, *args)
}

@Entity
class User (val name : String,
            val age : Int,
            @Id @GeneratedValue(strategy = GenerationType.AUTO)
            val id: Long = -1)

interface UserRepository : JpaRepository<User, Long> {
    fun findByName(username: String)
}

@RestController
@RequestMapping("/user")
class UserController(val userRepository: UserRepository) {

    @GetMapping("/all")
    fun getAllUsers() = userRepository.findAll()

    @GetMapping("/{username}")
    fun findUsername(@PathVariable  username :String) = userRepository.findByName(username)

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    fun insertUser(@RequestBody user : User) = userRepository.save(user)

}