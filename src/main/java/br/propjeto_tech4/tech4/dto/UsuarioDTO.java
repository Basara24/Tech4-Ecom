package br.propjeto_tech4.tech4.dto;

public record UsuarioDTO(String nome, String email) {
    // Este record é usado como DTO para transferir dados de criação ou atualização de Usuário.
}