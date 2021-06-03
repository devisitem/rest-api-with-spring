package me.kimchidev.demorestapi.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountService {


    @Autowired
    private AccountRepository accountRepository;


}
