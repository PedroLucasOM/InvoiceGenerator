package com.system.invoicegenerator.mapper;

import com.system.invoicegenerator.model.CreditCard;
import com.system.invoicegenerator.model.Transaction;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.BindException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ImportDataLineMapperConfig {

    @Bean
    public PatternMatchingCompositeLineMapper<Object> importDataLineMapper() {
        PatternMatchingCompositeLineMapper<Object> patternMatchingCompositeLineMapper = new PatternMatchingCompositeLineMapper<Object>();
        patternMatchingCompositeLineMapper.setTokenizers(tokenizers());
        patternMatchingCompositeLineMapper.setFieldSetMappers(fieldSetMappers());
        return patternMatchingCompositeLineMapper;
    }

    private Map<String, FieldSetMapper<Object>> fieldSetMappers() {
        Map<String, FieldSetMapper<Object>> fieldSetMapperMap = new HashMap<>();
        fieldSetMapperMap.put("0*", creditCardFieldSetMapper());
        fieldSetMapperMap.put("1*", transactionFieldSetMapper());
        return fieldSetMapperMap;
    }

    private FieldSetMapper<Object> transactionFieldSetMapper() {
        return new FieldSetMapper<Object>() {
            @Override
            public Transaction mapFieldSet(FieldSet fieldSet) throws BindException {
                Transaction transaction = new Transaction();
                transaction.setId(fieldSet.readInt("id"));
                transaction.setCreditCardNumber(fieldSet.readInt("creditCardNumber"));
                transaction.setDescription(fieldSet.readString("description"));
                transaction.setValue(fieldSet.readDouble("value"));
                transaction.setDate(new Date(fieldSet.readDate("date", "yyyy-MM-dd").getTime()));
                return transaction;
            }
        };
    }

    private FieldSetMapper<Object> creditCardFieldSetMapper() {
        return new FieldSetMapper<Object>() {
            @Override
            public CreditCard mapFieldSet(FieldSet fieldSet) throws BindException {
                CreditCard creditCard = new CreditCard();
                creditCard.setCreditCardNumber(fieldSet.readInt("creditCardNumber"));
                creditCard.setClient(fieldSet.readInt("client"));
                return creditCard;
            }
        };
    }

    private Map<String, LineTokenizer> tokenizers() {
        Map<String, LineTokenizer> lineTokenizerMap = new HashMap<>();
        lineTokenizerMap.put("0*", creditCardLineTokenizer());
        lineTokenizerMap.put("1*", transactionLineTokenizer());
        return lineTokenizerMap;
    }

    private LineTokenizer transactionLineTokenizer() {
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setNames("id", "creditCardNumber", "description", "value", "date");
        delimitedLineTokenizer.setIncludedFields(1, 2, 3, 4, 5);
        return delimitedLineTokenizer;
    }

    private LineTokenizer creditCardLineTokenizer() {
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setNames("creditCardNumber", "client");
        delimitedLineTokenizer.setIncludedFields(1, 2);
        return delimitedLineTokenizer;
    }

}
