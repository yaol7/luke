/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.lucene.luke.models.overview;

import org.apache.lucene.luke.util.IndexUtils;
import org.apache.lucene.store.AlreadyClosedException;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

public class OverviewImplTest extends OverviewTestBase {

  @Test
  public void testGetNumFields() throws Exception {
    OverviewImpl overview = new OverviewImpl();
    overview.reset(reader, indexDir.toString());
    assertEquals(2, (long) overview.getNumFields());
  }

  @Test
  public void testGetFieldNames() throws Exception {
    OverviewImpl overview = new OverviewImpl();
    overview.reset(reader, indexDir.toString());
    assertEquals(
        new HashSet<>(Arrays.asList("f1", "f2")),
        new HashSet<>(IndexUtils.getFieldNames(reader)));
  }

  @Test
  public void testGetNumDocuments() throws Exception {
    OverviewImpl overview = new OverviewImpl();
    overview.reset(reader, indexDir.toString());
    assertEquals(3, (long) overview.getNumDocuments());
  }

  @Test
  public void testHasDeletions() throws Exception {
    OverviewImpl overview = new OverviewImpl();
    overview.reset(reader, indexDir.toString());
    assertFalse(overview.hasDeletions());
  }

  @Test
  public void testGetNumDeletedDocs() throws Exception {
    OverviewImpl overview = new OverviewImpl();
    overview.reset(reader, indexDir.toString());
    assertEquals(0, (long) overview.getNumDeletedDocs());
  }

  @Test
  public void testIsOptimized() throws Exception {
    OverviewImpl overview = new OverviewImpl();
    overview.reset(reader, indexDir.toString());
    assertTrue(overview.isOptimized().orElse(false));
  }

  @Test
  public void testGetIndexVersion() throws Exception {
    OverviewImpl overview = new OverviewImpl();
    overview.reset(reader, indexDir.toString());
    assertTrue(overview.getIndexVersion().orElseThrow(IllegalStateException::new) > 0);
  }

  @Test
  public void testGetIndexFormat() throws Exception {
    OverviewImpl overview = new OverviewImpl();
    overview.reset(reader, indexDir.toString());
    assertEquals("Lucene 7.2 or later", overview.getIndexFormat());
  }

  @Test
  public void testGetDirImpl() throws Exception {
    OverviewImpl overview = new OverviewImpl();
    overview.reset(reader, indexDir.toString());
    assertEquals(dir.getClass().getName(), overview.getDirImpl());
  }

  @Test(expected = AlreadyClosedException.class)
  public void testClose() throws Exception {
    OverviewImpl overview = new OverviewImpl();
    overview.reset(reader, indexDir.toString());
    reader.close();
    overview.getNumFields();
  }

}
