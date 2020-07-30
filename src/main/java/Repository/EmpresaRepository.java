/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repository;

import Model.Empresa;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Julio
 */
public interface EmpresaRepository extends MongoRepository<Empresa, String>{
    
    public Empresa findByCnpj(String string);
    public List<Empresa> findByCnpjLikeOrFantasiaLikeIgnoreCaseOrRazaoSocialLikeIgnoreCase(String cnpj, String fantasia, String razaoSocial);
    public List<Empresa> findByCidadeLike(String cidade);
    
}
