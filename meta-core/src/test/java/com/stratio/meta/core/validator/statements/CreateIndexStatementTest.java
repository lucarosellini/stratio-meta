/*
 * Stratio Meta
 *
 * Copyright (c) 2014, Stratio, All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */

package com.stratio.meta.core.validator.statements;

import com.stratio.meta.core.validator.BasicValidatorTest;
import org.testng.annotations.Test;

public class CreateIndexStatementTest extends BasicValidatorTest {

    @Test
    public void validateDefaultNoNameOk(){
        String inputText = "CREATE DEFAULT INDEX ON demo.users (email);";
        validateOk(inputText, "validateDefaultNoNameOk");
    }

    @Test
    public void validateDefaultNamedOk(){
        String inputText = "CREATE DEFAULT INDEX new_index ON demo.users (email);";
        validateOk(inputText, "validateDefaultNamedOk");
    }

    @Test
    public void validateDefaultIfNotExistsOk(){
        String inputText = "CREATE DEFAULT INDEX IF NOT EXISTS users_gender_idx ON demo.users (gender);";
        validateOk(inputText, "validateDefaultIfNotExistsOk");
    }

    @Test
    public void validateDefaultExistsFail(){
        String inputText = "CREATE DEFAULT INDEX users_gender_idx ON demo.users (gender);";
        validateFail(inputText, "validateDefaultExistsFail");
    }

    @Test
    public void validateNotExistsTablename(){
        String inputText = "CREATE DEFAULT INDEX users_gender_idx ON demo.unknown (gender);";
        validateFail(inputText, "validateNotExistsTablename");
    }

    @Test
    public void validateNotExistsKeyspace(){
        String inputText = "CREATE DEFAULT INDEX users_gender_idx ON unknown.users (gender);";
        validateFail(inputText, "validateNotExistsKeyspace");
    }

    //
    // --- Lucene ---
    //

    @Test
    public void validateLuceneNoNameOk(){
        String inputText = "CREATE LUCENE INDEX ON demo.types (varchar_column);";
        validateOk(inputText, "validateLuceneNoNameOk");
    }

    @Test
    public void validateLuceneNamedOk() {
        String inputText = "CREATE LUCENE INDEX new_index ON demo.types (varchar_column);";
        String expectedText = inputText.replace("INDEX new_index ON", "INDEX stratio_lucene_new_index ON");
        validateOk(inputText, expectedText, "validateLuceneNamedOk");
    }

    @Test
    public void validateLucene2columnsOk() {
        String inputText = "CREATE LUCENE INDEX new_index ON demo.types (varchar_column, boolean_column);";
        String expectedText = inputText.replace("INDEX new_index ON", "INDEX stratio_lucene_new_index ON");
        validateOk(inputText, expectedText, "validateLucene2columnsOk");
    }

    @Test
    public void validateLuceneStratioNameFail() {
        String inputText = "CREATE LUCENE INDEX stratio_new_index ON demo.types (varchar_column, boolean_column);";
        String expectedText = inputText.replace("INDEX stratio_new_index ON", "INDEX stratio_lucene_stratio_new_index ON");
        validateFail(inputText, expectedText, "validateLuceneStratioNameFail");
    }

    @Test
    public void validateLucene2indexesFail(){
        String inputText = "CREATE LUCENE INDEX ON demo.users (gender);";
        validateFail(inputText, "validateLucene2indexesFail");
    }

    @Test
    public void validateLuceneStratioColumnFail() {
        String inputText = "CREATE LUCENE INDEX new_index ON demo.users (email, name, stratio_lucene_index_1);";
        String expectedText = inputText.replace("INDEX new_index ON", "INDEX stratio_lucene_new_index ON");
        validateFail(inputText, expectedText, "validateLuceneStratioColumnFail");
    }

    @Test
    public void validateLuceneWithOptionsFail() {
        String inputText = "CREATE LUCENE INDEX new_index ON demo.users (email, bool, age)"
                + " WITH OPTIONS = {'refresh_seconds': '1'};";
        String expectedText = inputText.replace("INDEX new_index ON", "INDEX stratio_lucene_new_index ON");
        validateFail(inputText, expectedText, "validateLuceneWithOptionsFail");
    }

    @Test
    public void validateLuceneWithOptionsFullFail() {
        String inputText = "CREATE LUCENE INDEX demo_banks ON demo.banks (lucene) "
                + "USING org.apache.cassandra.db.index.stratio.RowIndex WITH OPTIONS = "
                + "{schema: '{default_analyzer:\"org.apache.lucene.analysis.standard.StandardAnalyzer\", fields: {day: {type: \"date\", pattern: \"yyyy-MM-dd\"}, key: {type:\"uuid\"}}}'};";
        String expectedText = inputText.replace("INDEX demo_banks ON", "INDEX stratio_lucene_demo_banks ON");
        validateFail(inputText, expectedText, "validateLuceneWithOptionsFullFail");
    }
    
}
