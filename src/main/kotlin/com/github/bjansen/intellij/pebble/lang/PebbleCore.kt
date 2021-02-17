package com.github.bjansen.intellij.pebble.lang

import com.github.bjansen.intellij.pebble.utils.ResourceUtil
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiMethod

object PebbleCore {

    private val filtersKey = Key.create<PsiClass>("PEBBLE_FILTERS_CLASS")
    private val testsKey = Key.create<PsiClass>("PEBBLE_TESTS_CLASS")

    private val filtersByProject = hashMapOf<Project, Map<String, Filter>>()
    private val testsByProject = hashMapOf<Project, Map<String, Test>>()

    fun getFilters(project: Project): Collection<Filter> {
        return filtersByProject.computeIfAbsent(project, this::initFilters).values
    }

    fun getFilter(name: String, project: Project): Filter? {
        return filtersByProject.computeIfAbsent(project, this::initFilters)[name]
    }

    fun getTests(project: Project): Collection<Test> {
        return testsByProject.computeIfAbsent(project, this::initTests).values
    }

    fun getTest(name: String, project: Project): Test? {
        return testsByProject.computeIfAbsent(project, this::initTests)[name]
    }

    private fun initFilters(project: Project): Map<String, Filter> {
        val filtersClass =
            ResourceUtil.loadPsiClassFromFile("/implicitCode/Filters.java", filtersKey, project)

        return filtersClass?.methods?.map { Filter(it) }?.map { it.name to it }?.toMap() ?: emptyMap()
    }

    private fun initTests(project: Project): Map<String, Test> {
        val testsClass =
            ResourceUtil.loadPsiClassFromFile("/implicitCode/Tests.java", testsKey, project)

        return testsClass?.methods?.map { Test(it) }?.map { it.name to it }?.toMap() ?: emptyMap()
    }
}

class Filter(val source: PsiMethod) {
    private var idx = 0;

    val name = source.name.replace("$$", "")

    val parameters = source.parameterList.parameters.associate { p ->
        (p.name?.replace("$$", "") ?: "arg${idx++}") to p.type
    }
}

class Test(val source: PsiMethod) {

    val name = source.name.replace("$$", "")
}
