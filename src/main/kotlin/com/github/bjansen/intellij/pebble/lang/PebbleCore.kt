package com.github.bjansen.intellij.pebble.lang

import com.github.bjansen.intellij.pebble.utils.ResourceUtil
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiMethod

object PebbleCore {

    private val key = Key.create<PsiClass>("PEBBLE_FILTERS_CLASS")

    private val filtersByProject = hashMapOf<Project, Map<String, Filter>>()

    fun getFilters(project: Project): Collection<Filter> {
        if (!filtersByProject.containsKey(project)) {
            initFilters(project)
        }

        return filtersByProject[project]!!.values
    }

    fun getFilter(name: String, project: Project): Filter? {
        if (!filtersByProject.containsKey(project)) {
            initFilters(project)
        }

        return filtersByProject[project]?.get(name)
    }

    private fun initFilters(project: Project) {
        val loopClass = ResourceUtil.loadPsiClassFromFile("/implicitCode/Filters.java", key, project)
        val filters = hashMapOf<String, Filter>()

        loopClass?.methods?.forEach {
            val filter = Filter(it)
            filters[filter.name] = filter
        }

        filtersByProject.put(project, filters)
    }
}

class Filter(val source: PsiMethod) {
    private var idx = 0;

    val name = source.name.replace("$$", "")

    val parameters = source.parameterList.parameters.associate {
            p -> (p.name?.replace("$$", "") ?: "arg${idx++}") to p.type
    }
}
