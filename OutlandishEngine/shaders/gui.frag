#version 330 core

in vec2 pass_textureCoords;

out vec4 out_Color;

uniform vec4 color;
uniform sampler2D tex;

void main(void) {
	
	vec4 texVec = texture(tex, pass_textureCoords);
	out_Color = vec4(color.xyz*(1-texVec.w), color.w) + vec4(texVec.xyz*texVec.w, texVec.w);
}