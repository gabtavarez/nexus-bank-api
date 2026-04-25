package com.tavarez.nexusbank.services;

import com.tavarez.nexusbank.dto.request.AccountRequestDTO;
import com.tavarez.nexusbank.dto.response.AccountResponseDTO;
import com.tavarez.nexusbank.entities.Account;
import com.tavarez.nexusbank.entities.User;
import com.tavarez.nexusbank.enums.AccountStatus;
import com.tavarez.nexusbank.exceptions.BusinessException;
import com.tavarez.nexusbank.repositories.AccountRepository;
import com.tavarez.nexusbank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Random;

/**
 * Serviço responsável pela gestão de contas bancárias no Nexus Bank.
 * Gerencia a abertura de novas contas e garante a unicidade do número da conta.
 */
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Abre uma nova conta bancária para um usuário.
     * Valida a existência do usuário, gera um número único de 6 dígitos e inicia o saldo com zero.
     *
     * @param dto Contém o ID do usuário e o tipo de conta (CHECKING/SAVINGS).
     * @return AccountResponseDTO com os dados da conta criada.
     * @throws BusinessException se o usuário não for encontrado.
     */
    @Transactional
    public AccountResponseDTO createAccount(AccountRequestDTO dto) {
        // Busca o dono da conta no banco de dados
        User owner = userRepository.findById(dto.userId())
                .orElseThrow(() -> new BusinessException("User not found with ID: " + dto.userId()));

        // Constrói a conta com número aleatório e saldo zerado
        Account account = Account.builder()
                .accountNumber(generateUniqueAccountNumber())
                .balance(BigDecimal.ZERO)
                .type(dto.type())
                .status(AccountStatus.ACTIVE)
                .owner(owner)
                .build();

        account = accountRepository.save(account);

        return new AccountResponseDTO(
                account.getId(),
                account.getAccountNumber(),
                account.getBalance(),
                account.getType(),
                account.getStatus()
        );
    }

    /**
     * Gera um número de conta aleatório e verifica no repositório se ele já existe.
     * @return String de 6 dígitos únicos.
     */
    private String generateUniqueAccountNumber() {
        Random random = new Random();
        String number;
        boolean exists;

        do {
            number = String.valueOf(100000 + random.nextInt(900000));
            exists = accountRepository.existsByAccountNumber(number);
        } while (exists);

        return number;
    }
}