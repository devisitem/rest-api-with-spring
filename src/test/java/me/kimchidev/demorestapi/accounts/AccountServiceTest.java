package me.kimchidev.demorestapi.accounts;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class AccountServiceTest {

    @Autowired
    AccountService accountService;

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
        //when

        UserDetailsService userDetailsService = accountService;
        UserDetails kimchi = userDetailsService.loadUserByUsername("kimchi");

        //then
        assertEquals(kimchi.getPassword(),password);
        assertEquals(kimchi.(),userName);

    }
}