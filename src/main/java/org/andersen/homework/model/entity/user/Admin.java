package org.andersen.homework.model.entity.user;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@DiscriminatorValue("ADMIN")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Admin extends User {
}
