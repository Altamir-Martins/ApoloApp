package com.apolo.app.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel : ViewModel() {
    
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()
    
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun login(email: String, password: String) {
        _isLoading.value = true
        // Simulação de login
        try {
            Thread.sleep(2000) // Simular delay de rede
            _currentUser.value = User(
                id = "user_123",
                name = "Usuário Anônimo",
                email = email,
                isAnonymous = false
            )
            _isLoggedIn.value = true
            _errorMessage.value = null
        } catch (e: Exception) {
            _errorMessage.value = "Erro ao fazer login"
        }
        _isLoading.value = false
    }

    fun loginAnonymously() {
        _isLoading.value = true
        try {
            Thread.sleep(1000)
            _currentUser.value = User(
                id = "anon_${System.currentTimeMillis()}",
                name = "Usuário Anônimo",
                email = "",
                isAnonymous = true
            )
            _isLoggedIn.value = true
            _errorMessage.value = null
        } catch (e: Exception) {
            _errorMessage.value = "Erro ao entrar anonimamente"
        }
        _isLoading.value = false
    }

    fun register(name: String, cpf: String, email: String, password: String) {
        _isLoading.value = true
        try {
            Thread.sleep(2000)
            _currentUser.value = User(
                id = "user_${System.currentTimeMillis()}",
                name = name,
                email = email,
                isAnonymous = false
            )
            _isLoggedIn.value = true
            _errorMessage.value = null
        } catch (e: Exception) {
            _errorMessage.value = "Erro ao criar conta"
        }
        _isLoading.value = false
    }

    fun logout() {
        _isLoggedIn.value = false
        _currentUser.value = null
    }

    fun clearError() {
        _errorMessage.value = null
    }
}

data class User(
    val id: String,
    val name: String,
    val email: String,
    val isAnonymous: Boolean
)