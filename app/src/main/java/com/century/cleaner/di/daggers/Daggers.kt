package com.century.incoming.daggers

import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.support.annotation.VisibleForTesting
import java.lang.reflect.Method
import java.util.Stack

object Daggers {


  @VisibleForTesting
  private var appComponent: Any? = null
  private val componentStack = Stack<Any>()
  private val activityLifecycleCallbacks = DaggerActivityLifecycleCallbacks()

  fun clear() {
    appComponent = null
    componentStack.clear()
  }

  fun closeActivityScope() {
    if (componentStack.empty()) {
      return
    }
    componentStack.pop()
  }

  fun configure(app: Application) {
    app.unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks)
    app.registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
  }

  fun inject(target: Any) {
    inject(topScope, target)
  }

  fun injectAppDependencies(target: Any) {
    inject(appComponent!!, target)
  }

  fun openActivityScope(activityModule: Any) {
    val activityComponent = makeSubComponent(appComponent!!, activityModule)
    componentStack.push(activityComponent)
  }

  fun openAppScope(appComponent: Any) {
    Daggers.appComponent = appComponent
  }

  private val topScope: Any
    @VisibleForTesting
    get() {
      if (componentStack.empty()) {
        throw IllegalStateException("there is no opened scope")
      }
      return componentStack.peek()
    }

  /**
   * Find method with given name and parameter on the target object.
   *
   * @param target    object
   * @param name      method name
   * @param parameter method parameter
   * @return Method or throw Exception.
   */
  private fun findMethod(target: Class<*>, name: String, parameter: Any): Method {
    val methods = target.declaredMethods
    for (method in methods) {
      if (method.name == name) {
        val parameterTypes = method.parameterTypes
        if (parameterTypes.size == 1 && parameterTypes[0].isAssignableFrom(parameter.javaClass)) {
          return method
        }
      }
    }
    throw IllegalArgumentException(
        "Can not find method "
            + target.name
            + "."
            + name
            + "("
            + parameter
            + ")")
  }

  /**
   * Find inject(target) method on component then involve component.inject(target)
   *
   * @param component the dagger component
   * @param target    the target object
   */
  private fun inject(component: Any, target: Any) {
    val componentClass = component.javaClass
    val componentInterfaceClass: Class<*>
    if (componentClass.isInterface) {
      componentInterfaceClass = componentClass
    } else {
      componentInterfaceClass = componentClass.interfaces[0]
    }
    val injectMethod = findMethod(componentInterfaceClass, "inject", target)
    try {
      injectMethod.invoke(component, target)
    } catch (e: Exception) {
      e.printStackTrace()
    }

  }

  /**
   * Find plus(module) on component then involve component.plus(module) to return sub-module.
   *
   * @param component the dagger component
   * @param module    the dagger module
   * @return sub-component from component.plus(module)
   */
  private fun makeSubComponent(component: Any, module: Any): Any {
    val componentClass = component.javaClass
    val plusMethod = findMethod(componentClass, "plus", module)
    try {
      return plusMethod.invoke(component, module)
    } catch (e: Exception) {
      throw IllegalArgumentException("can not create component with " + module.javaClass
          .name)
    }

  }

}