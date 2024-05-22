package eci.cvds.armario.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import eci.cvds.armario.model.PrendaUsuario;
import eci.cvds.armario.model.User;
import eci.cvds.armario.model.Prenda;
import eci.cvds.armario.repository.SessionRepository;
import eci.cvds.armario.repository.PrendaUsuarioRepository;
import eci.cvds.armario.repository.PrendaRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.UUID;
@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://witty-field-0ab72731e.5.azurestaticapps.net"})
@RequestMapping(value = "/user")
public class prendaUsuarioController {
    private SessionRepository sessionRepository;
    private PrendaUsuarioRepository prendaUsuarioRepository;
    private PrendaRepository prendaRepository;

    public prendaUsuarioController(SessionRepository sessionRepository, PrendaUsuarioRepository prendaUsuarioRepository) {
        this.sessionRepository = sessionRepository;
        this.prendaUsuarioRepository = prendaUsuarioRepository;
    }

    @GetMapping("/client/UsuarioPrendas")
    public List<PrendaUsuario> getAllPrendasOfUser(@RequestHeader("authToken") UUID authToken) {
        User user = this.sessionRepository.findByToken(authToken).getUser();
        return prendaUsuarioRepository.findByUser(user);
    }
    @GetMapping("/client/UsuarioPrenda/{idPrenda}")
    public ResponseEntity<PrendaUsuario> getPrendaById(@RequestHeader("authToken") UUID authToken, @PathVariable("idPrenda") UUID idPrenda) {
        User user = this.sessionRepository.findByToken(authToken).getUser();
        Prenda prenda = prendaRepository.findByPrendaId(idPrenda);
        PrendaUsuario prendaUser = prendaUsuarioRepository.findByPrenda(prenda);
        if (user != null && prenda != null) {
            return new ResponseEntity<>(prendaUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }
    @PostMapping("/client/UsuarioPrenda")
    public PrendaUsuario addPrenda(@RequestBody PrendaUsuario prendaUser, @RequestHeader("authToken") UUID authToken) {
        User user = this.sessionRepository.findByToken(authToken).getUser();
        return prendaUsuarioRepository.save(prendaUser);
    }

}