package com.colruyt.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

public class DemanRequestKey implements Serializable {
    private Long reqId;

    private String role;
}
