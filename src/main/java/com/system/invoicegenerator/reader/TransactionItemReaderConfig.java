package com.system.invoicegenerator.reader;

import com.system.invoicegenerator.model.Client;
import com.system.invoicegenerator.model.CreditCard;
import com.system.invoicegenerator.model.Transaction;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Configuration
public class TransactionItemReaderConfig {

    @Bean
    public JdbcCursorItemReader<Transaction> transactionItemReader(
            @Qualifier("appDataSource") DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<Transaction>()
                .name("transactionItemReader")
                .dataSource(dataSource)
                .sql("select * from transaction join credit_card using (credit_card_number) order by credit_card_number")
                .rowMapper(rowMapperTransaction())
                .build();
    }

    private RowMapper<Transaction> rowMapperTransaction() {
        return new RowMapper<Transaction>() {

            @Override
            public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
                CreditCard creditCard = new CreditCard();
                creditCard.setCreditCardNumber(rs.getInt("credit_card_number"));
                Client client = new Client();
                client.setId(rs.getInt("client"));
                creditCard.setClient(client);

                Transaction transaction = new Transaction();
                transaction.setId(rs.getInt("id"));
                transaction.setCreditCard(creditCard);
                transaction.setDate(rs.getDate("date"));
                transaction.setValue(rs.getDouble("value"));
                transaction.setDescription(rs.getString("description"));

                return transaction;
            }

        };
    }

}
