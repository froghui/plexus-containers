package org.codehaus.plexus.component.factory.java;

import org.codehaus.plexus.component.factory.AbstractComponentFactory;
import org.codehaus.plexus.component.repository.ComponentDescriptor;


/**
 * Component Factory for components written in Java Language which have default no parameter constructor
 *
 * @author <a href="mailto:jason@maven.org">Jason van Zyl</a>
 * @author <a href="mailto:mmaczka@interia.pl">Michal Maczka</a>
 *
 * @version $Id$
 */
public class JavaComponentFactory
    extends AbstractComponentFactory
{
    
    /**
     * 
     * @todo which exception shold be thrown if '!implementationMatch'? 
     */
    public Object newInstance( ComponentDescriptor componentDescriptor, ClassLoader classLoader )
        throws ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        
        String role = componentDescriptor.getRole();
        
        String roleHint = componentDescriptor.getRoleHint();
        
        String implementation = componentDescriptor.getImplementation();
                
        Class roleClass = classLoader.loadClass( role );
        
        Class implementationClass = classLoader.loadClass( implementation );
        
        boolean implementationMatch = roleClass.isAssignableFrom( implementationClass );
        
        
        if ( !implementationMatch )
        {
            StringBuffer msg = new StringBuffer( "Instance of component " + componentDescriptor.getHumanReadableKey() );
            
            msg.append( " cannot be created. Role class: '" + role + "' " );
            
            msg.append( " is neither a superclass nor a superinterface of implementation class: ' " + implementation +"'" );
            
            throw new InstantiationException( msg.toString() );
        }
        
        Object instance = implementationClass.newInstance();

        return instance;
    }
}