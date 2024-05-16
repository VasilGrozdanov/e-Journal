package nbu.bg.logisticscompany.model.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
public class Admin extends User {}
