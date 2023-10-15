package br.com.dudalima.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;


/*
 * Modificador
 * public
 * private
 * protected
*/

@RestController
@RequestMapping("/users")
public class UserController {
   /*
    * String (txt)
    * Integer (int) números inteiros
    * Double números com casas decimais
    * Float Números 0.000
    * Char (A C)
    * Date (data)
    * Void ( não tem nenhum retorno, ele só executa a lógica)
    */


    @Autowired
    private IUserRepository userRepository;


    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel) {
        var user = this.userRepository.findByUsername(userModel.getUsername());

        if (user != null){
            // mensagem de erro
            // status code
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");

        }

        var passwordHashred = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());

        userModel.setPassword(passwordHashred);
        


        var userCreated = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.OK).body(userCreated);

    }

}
