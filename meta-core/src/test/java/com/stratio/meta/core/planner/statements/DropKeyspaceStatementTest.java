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

package com.stratio.meta.core.planner.statements;

import com.stratio.meta.core.planner.BasicPlannerTest;
import com.stratio.meta.core.statements.DropKeyspaceStatement;
import org.testng.annotations.Test;

public class DropKeyspaceStatementTest  extends BasicPlannerTest {

    @Test
    public void testPlanForDropKeyspace(){
        String inputText = "DROP KEYSPACE demo;";
        stmt = new DropKeyspaceStatement("demo", false);
        validateCassandraPath("testPlanForDropKeyspace");
    }
}
