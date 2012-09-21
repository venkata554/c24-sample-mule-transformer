/*
 * Copyright 2012 C24 Technologies.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *                      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package biz.c24.io.mule;

import biz.c24.io.api.data.ComplexDataObject;
import biz.c24.io.api.data.ValidationException;
import biz.c24.io.api.data.ValidationManager;

/**
 * Simple wrapper around the iO ValidationManager to make it easier to embed in a Mule ESB flow
 * 
 * 
 * @author Andrew Elmore
 *
 * @param <T> The type of ComplexDataObject we are validating
 */
public class C24ValidatingFilter<T extends ComplexDataObject> {
	
	public T validate(T obj) throws ValidationException {
		ValidationManager mgr = new ValidationManager();
		mgr.validateByException(obj);
		return obj;
	}

}
