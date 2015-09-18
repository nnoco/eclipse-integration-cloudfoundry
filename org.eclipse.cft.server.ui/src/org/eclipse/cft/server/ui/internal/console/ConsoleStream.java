/*******************************************************************************
 * Copyright (c) 2014 Pivotal Software, Inc. 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, 
 * Version 2.0 (the "License�); you may not use this file except in compliance 
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *  
 *  Contributors:
 *     Pivotal Software, Inc. - initial API and implementation
 ********************************************************************************/
package org.eclipse.cft.server.ui.internal.console;

import java.io.IOException;

import org.eclipse.cft.server.core.internal.CloudErrorUtil;
import org.eclipse.cft.server.core.internal.log.CloudLog;
import org.eclipse.cft.server.core.internal.log.LogContentType;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.console.IOConsoleOutputStream;

/**
 * Basic console stream that manages an output stream to an Eclipse console,
 * including initialising the stream, as well as closing streams.
 * 
 * <p/>
 * IMPORTANT NOTE: Keep this INTERNAL Use as API is still evolving.
 * @since 1.7.0
 */
public abstract class ConsoleStream {

	protected ConsoleStream() {

	}

	abstract public void initialiseStream(ConsoleConfig descriptor) throws CoreException;

	abstract public void close();

	abstract public boolean isActive();

	abstract protected IOConsoleOutputStream getOutputStream(LogContentType contentType);

	public synchronized void write(CloudLog log) throws CoreException {
		if (log == null || log.getMessage() == null) {
			return;
		}
		IOConsoleOutputStream outStream = getOutputStream(log.getLogType());

		if (outStream != null) {
			try {
				outStream.write(log.getMessage());
			}
			catch (IOException e) {
				throw CloudErrorUtil.toCoreException(e);
			}
		}
	}

}