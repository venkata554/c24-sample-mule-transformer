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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import org.mule.api.transformer.TransformerException;
import org.mule.config.i18n.MessageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import biz.c24.io.api.data.ComplexDataObject;
import biz.c24.io.api.data.Element;
import biz.c24.io.api.presentation.Source;

/**
 * Simple wrapper around the iO parsing process to make it easier to embed in a Mule ESB flow
 * 
 * 
 * @author Andrew Elmore
 *
 * @param <T> The type of ComplexDataObject we expect the parser to return
 */
public class C24Parser<T extends ComplexDataObject> {
	
	private static Logger LOG = LoggerFactory.getLogger(C24Parser.class);
	
    private Element element;
    private String encoding = "UTF-8";
   

    public void setElement(Element element) {
        this.element = element;
    }
   
    public void setElement(String element) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        this.element = (Element) Class.forName(element).newInstance();
    }
   
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
   
    public String getEncoding() {
        return encoding;
    }

    public T transform(InputStream iStream) throws TransformerException {
        return transform(new InputStreamReader(iStream));
    }
    
    public T transform(String str) throws TransformerException {
    	return transform(new StringReader(str));
    }


	public T transform(Reader reader) throws TransformerException {
        Source source = element.getModel().source();
        source.setReader(reader);
        source.setEncoding(encoding);

        
        try {
	        @SuppressWarnings("unchecked")
			T obj = (T) source.readObject(element);
	        
	        LOG.debug("Successfully parsed object of type {}", obj.getName());
	        
	        return obj;
        } catch (IOException e) {
			throw new TransformerException(MessageFactory.createStaticMessage("Parse failed"), e);
		}
	}

}
