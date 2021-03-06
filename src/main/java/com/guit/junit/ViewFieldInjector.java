/*
 * Copyright 2010 Gal Dolber.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.guit.junit;

import com.google.inject.MembersInjector;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

public class ViewFieldInjector<I> implements MembersInjector<I> {

  private final Field field;

  public ViewFieldInjector(Field field) {
    field.setAccessible(true);
    this.field = field;
  }

  @Override
  public void injectMembers(I i) {
    try {
      Class<?> type = (Class<?>) field.getType();
      field.set(i, Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type},
          new PojoInvocationHandler(type)));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
