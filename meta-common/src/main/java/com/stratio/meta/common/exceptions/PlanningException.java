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

package com.stratio.meta.common.exceptions;

/**
 * Planning exception thrown by the Driver if the statement could not be planned.
 */
public class PlanningException extends Exception{

  /**
   * Serial version UID in order to be {@link java.io.Serializable}.
   */
  private static final long serialVersionUID = 1878003904827417242L;

  public PlanningException(String message){
    super(message);
  }

}
