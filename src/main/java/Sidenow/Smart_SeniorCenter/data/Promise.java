package Sidenow.Smart_SeniorCenter.data;

import Sidenow.Smart_SeniorCenter.user.entity.User;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

public class Promise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;
    private Date date;

    private int current_num;
    private int total_num;

    @OneToMany(mappedBy = "promise",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<User> userList;


}
