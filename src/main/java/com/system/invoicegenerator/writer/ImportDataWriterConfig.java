package com.system.invoicegenerator.writer;

import com.system.invoicegenerator.model.CreditCard;
import com.system.invoicegenerator.model.Transaction;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Configuration
public class ImportDataWriterConfig {

    @Bean
    public JdbcBatchItemWriter<Object> creditCardFileWriter(
            @Qualifier("appDataSource") DataSource dataSource
    ) {
        return new JdbcBatchItemWriterBuilder<Object>()
                .dataSource(dataSource)
                .sql("INSERT INTO credit_card (credit_card_number, client) VALUES (?, ?)")
                .itemPreparedStatementSetter(itemPreparedStatementSetterCreditCard())
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<Object> transactionFileWriter(
            @Qualifier("appDataSource") DataSource dataSource
    ) {
        return new JdbcBatchItemWriterBuilder<Object>()
                .dataSource(dataSource)
                .sql("INSERT INTO transaction (id, credit_card_number, description, value, date) VALUES (?, ?, ?, ?, ?)")
                .itemPreparedStatementSetter(itemPreparedStatementSetterTransaction())
                .build();
    }

    private ItemPreparedStatementSetter<Object> itemPreparedStatementSetterTransaction() {
        return new ItemPreparedStatementSetter<Object>() {
            @Override
            public void setValues(Object object, PreparedStatement preparedStatement) throws SQLException {
                Transaction transaction = (Transaction) object;
                preparedStatement.setInt(1, transaction.getId());
                preparedStatement.setInt(2, transaction.getCreditCardNumber());
                preparedStatement.setString(3, transaction.getDescription());
                preparedStatement.setDouble(4, transaction.getValue());
                preparedStatement.setDate(5, new Date(transaction.getDate().getTime()));
            }
        };
    }

    private ItemPreparedStatementSetter<Object> itemPreparedStatementSetterCreditCard() {
        return new ItemPreparedStatementSetter<Object>() {
            @Override
            public void setValues(Object object, PreparedStatement preparedStatement) throws SQLException {
                CreditCard creditCard = (CreditCard) object;
                preparedStatement.setInt(1, creditCard.getCreditCardNumber());
                preparedStatement.setInt(2, creditCard.getClient());
            }
        };
    }

}
