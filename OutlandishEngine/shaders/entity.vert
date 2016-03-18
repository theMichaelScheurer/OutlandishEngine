#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 textureCoords;

out vec2 pass_textureCoords;

uniform mat4 transform;
uniform mat4 projection;
uniform mat4 view;

void main(void){
	
	vec4 worldpos = transform * vec4(position,1.0);
	gl_Position = projection * view * worldpos;
	pass_textureCoords = textureCoords;
}