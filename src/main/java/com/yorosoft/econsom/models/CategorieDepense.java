package com.yorosoft.econsom.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categorie_depense")
public class CategorieDepense extends Auditable<String> {


}
