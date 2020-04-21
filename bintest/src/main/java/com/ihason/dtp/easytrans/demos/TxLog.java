package com.ihason.dtp.easytrans.demos;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "tx_log")
@Entity
@Data
public class TxLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Lob
    private BigObject content;
}
