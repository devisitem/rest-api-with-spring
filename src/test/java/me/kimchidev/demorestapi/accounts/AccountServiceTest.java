package me.kimchidev.demorestapi.accounts;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void findByUserName() throws Exception {
        //given
        String password = "kimchi";
        String userName = "kimchidev@gmail.com";
        Account account = Account.builder()
                .email("kimchidev@gmail.com")
                .password("kimchi")
                .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                .build();
        this.accountRepository.save(account);

        //when

        UserDetailsService userDetailsService = accountService;
        UserDetails kimchi = userDetailsService.loadUserByUsername(userName);

        //then
        assertEquals(kimchi.getPassword(),password);

    }
}