package me.kimchidev.demorestapi.accounts;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.Assertions.*;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.internal.runners.statements.ExpectException;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class  AccountServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;


    @Test
    public void findByUserName() throws Exception {
        //Given
        String username = "kimchidev@gmail.com";
        String password = "kimchi";
        Account account = Account.builder()
                .email("kimchidev@gmail.com")
                .password("kimchi")
                .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                .build();
        //When
        accountRepository.save(account);
        UserDetailsService userDetailsService = (UserDetailsService) accountService;
        UserDetails userDetails = userDetailsService.loadUserByUsername("kimchidev@gmail.com");

        //Then
        assertEquals(userDetails.getPassword(),password);
        assertEquals(userDetails.getUsername(),username);

    }
    @Test
    public void findByUserNameFail() throws Exception {
        //given
        String userName = "testKimchi@test.com";
        expectedException.expect(UsernameNotFoundException.class);
        expectedException.expect(Matchers.containsString(userName ));


        //when
        accountService.loadUserByUsername(userName);

        
        //then
    
    }
}