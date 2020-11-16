package br.com.hub.avaliacao.mapper;

import br.com.hub.avaliacao.dto.CreditCardDTO;
import br.com.hub.avaliacao.entity.CreditCard;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreditCardMapper extends EntityMapper<CreditCardDTO, CreditCard> {

    CreditCardDTO toDto(CreditCard creditCard);

    CreditCard toEntity(CreditCardDTO creditCardDTO);

    default CreditCard fromId(String id) {
        if (id == null) {
            return null;
        }
        CreditCard entidade = new CreditCard();
        entidade.setId(id);
        return entidade;
    }
}
