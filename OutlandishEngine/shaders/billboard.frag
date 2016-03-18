#version 330 core

in vec2 pass_textureCoords;

out vec4 out_Color;

uniform sampler2D tex;

void main(void) {
	
	out_Color = texture(tex, pass_textureCoords);
}